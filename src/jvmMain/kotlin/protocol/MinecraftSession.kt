package com.gabrielleeg1.bedrockvoid.protocol

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.utils.getPacketId
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingCodec
import com.gabrielleeg1.bedrockvoid.protocol.types.readVarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.toHexString
import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarInt
import com.gabrielleeg1.bedrockvoid.protocol.utils.compress
import com.nukkitx.network.raknet.RakNetServerSession
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import mu.KLogging
import java.net.InetSocketAddress

class MinecraftSession(
  private val session: RakNetServerSession,
  private val codec: EncodingCodec,
) {
  val address: InetSocketAddress by session::address

  private val _inboundPacketFlow = MutableStateFlow<InboundPacket?>(null)
  val inboundPacketFlow: Flow<InboundPacket>
    get() = _inboundPacketFlow.filterNotNull()

  suspend fun onPacketReceived(buf: ByteBuf) {
    while (buf.isReadable) {
      val length = buf.readVarInt()
      val packetBuf = buf.readSlice(length)

      if (!packetBuf.isReadable) {
        error("Could not read packet")
      }

      val packetId = packetBuf.readVarInt()
      val decodingStrategy = codec.inboundPackets[packetId]
        ?: error("Packet ${packetId.toHexString()} does not have a deserializer")

      logger.debug {
        "Reading packet with id [${packetId.toHexString()}] and decoder [$decodingStrategy]"
      }

      decodingStrategy.run {
        _inboundPacketFlow.emit(codec.decodingStream(packetBuf).decodeT())
      }
    }
  }

  inline fun <reified T : InboundPacket> awaitPacket(): Flow<T> {
    return inboundPacketFlow.filterIsInstance()
  }

  suspend fun sendPacket(packet: OutboundPacket) {
    sendPackets(listOf(packet))
  }

  suspend fun sendPackets(packets: List<OutboundPacket>) {
    val uncompressed = ByteBufAllocator.DEFAULT.buffer(1 shl 3) // (packet list size) << 3

    packets.forEach { packet ->
      val id = getPacketId(packet::class)
      val packetBuf = ByteBufAllocator.DEFAULT.ioBuffer()

      packetBuf.writeVarInt( // write packet head
        0 or (id and 0x3ff)
          or (0 shl 0xa)
          or (0 shl 0xb)
      )

      @Suppress("UNCHECKED_CAST")
      val encodingStrategy = codec.outboundPackets[id]
        ?: error("Packet ${packet::class.simpleName} does not have an encoder")

      logger.debug {
        "Sending packet with id ${id.toHexString()} and encoding strategy $encodingStrategy"
      }

      encodingStrategy.run {
        codec.encodingStream(packetBuf).encodeT(packet)
      }

      uncompressed.writeVarInt(packetBuf.readableBytes())
      uncompressed.writeBytes(packetBuf)
    }

    val compressed = uncompressed.compress()

    val finalPayload = ByteBufAllocator.DEFAULT.ioBuffer(1 + compressed.readableBytes() + 8)
    finalPayload.writeByte(0xfe) // game packet id
    finalPayload.writeBytes(compressed)

    session.send(finalPayload)
  }

  suspend fun disconnect(message: String = "Disconnected") {
    sendPacket(DisconnectPacket(kickMessage = message))
  }

  companion object : KLogging()
}
