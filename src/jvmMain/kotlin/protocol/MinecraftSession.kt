package com.gabrielleeg1.bedrockvoid.protocol

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.utils.compress
import com.nukkitx.network.raknet.RakNetServerSession
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlin.reflect.full.findAnnotation

class MinecraftSession(
  private val session: RakNetServerSession,
  private val serializers: PacketSerializerMap,
  private val deserializers: PacketDeserializerMap,
) {
  private val _inboundPacketFlow = MutableStateFlow<InboundPacket?>(null)
  val inboundPacketFlow: Flow<InboundPacket>
    get() = _inboundPacketFlow.filterNotNull()

  suspend fun onPacketReceived(buf: ByteBuf) {
    while (buf.isReadable) {
      val length = buf.readVarInt()
      val packetBuffer = buf.readSlice(length.value)

      if (!packetBuffer.isReadable) {
        error("ByteBuf@readPacketBatch => Could not read packet")
      }

      val packetId = packetBuffer.readVarInt()
        .also { println("ByteBuf@readPacketBatch => Packet id: $it") }

      val deserializer = deserializers[packetId.value]
        ?.also { println("ByteBuf@readPacketBatch => Packet deserializer: $it") }
        ?: error("ByteBuf@readPacketBatch => Unknown packet deserializer for packet id: $packetId")

      deserializer.run {
        _inboundPacketFlow.emit(packetBuffer.deserialize())
      }
    }
  }

  inline fun <reified T : InboundPacket> awaitPacket(): Flow<T> {
    return inboundPacketFlow.filterIsInstance()
  }

  suspend fun <T : OutboundPacket> sendPacket(packet: T) {
    sendPackets(listOf(packet))
  }

  suspend fun <T : OutboundPacket> sendPackets(packets: List<T>) {
    val uncompressed = ByteBufAllocator.DEFAULT.buffer(1 shl 3) // (packet list size) << 3

    packets.forEach { packet ->
      val packetId = packet::class.findAnnotation<Packet>()
        ?.id
        ?: error("Packet ${packet::class.simpleName} does not have an id")

      val packetBuf = ByteBufAllocator.DEFAULT.ioBuffer()

      serializers[packetId]
        ?.run {
          val head = (
            0 or (packetId and 0x3ff)
              or ((0 and 3) shl 10)
              or ((0 and 3) shl 12)
            )

          packetBuf.writeVarInt(VarInt(head))
          packetBuf.serialize(packet)
        }
        ?: error("Packet ${packet::class.simpleName} does not have a serializer")

      uncompressed.writeVarInt(VarInt(packetBuf.readableBytes()))
      uncompressed.writeBytes(packetBuf)
    }

    val compressed = uncompressed.compress()

    val finalPayload = ByteBufAllocator.DEFAULT.ioBuffer(1 + compressed.readableBytes() + 8)
    finalPayload.writeByte(0xfe) // game packet id
    finalPayload.writeBytes(compressed)

    session.send(finalPayload)
  }
}
