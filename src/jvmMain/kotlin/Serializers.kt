package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.network.PacketDeserializer
import com.gabrielleeg1.bedrockvoid.network.PacketDeserializerMap
import com.gabrielleeg1.bedrockvoid.network.PacketSerializer
import com.gabrielleeg1.bedrockvoid.network.PacketSerializerMap
import com.gabrielleeg1.bedrockvoid.network.VarInt
import com.gabrielleeg1.bedrockvoid.network.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.network.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.network.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.network.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.network.packets.outbound.OutboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.network.readVarInt
import com.gabrielleeg1.bedrockvoid.network.writeVarInt
import io.netty.buffer.ByteBuf

private fun ByteBuf.readLEString(): String {
    val length = readIntLE()
    val data = ByteArray(length)

    readBytes(data)

    return data.decodeToString()
}

private object DisconnectPacketSerializer : PacketSerializer<DisconnectPacket> {
    override fun ByteBuf.serialize(value: DisconnectPacket) {
        writeBoolean(value.hideDisconnectPacket)
        writeVarInt(VarInt(value.kickMessage.length))
        writeBytes(value.kickMessage.toByteArray())
    }
}

private object LoginPacketDeserializer : PacketDeserializer<LoginPacket> {
    override fun ByteBuf.deserialize(): LoginPacket {
        val protocolVersion = readInt()

        val jwt = readSlice(readVarInt().value)

        val chainData = jwt.readLEString()
        val skinData = jwt.readLEString()

        return LoginPacket(protocolVersion, chainData, skinData)
    }
}

private object ViolationWarningPacketDeserializer : PacketDeserializer<ViolationWarningPacket> {
    override fun ByteBuf.deserialize(): ViolationWarningPacket {
        return ViolationWarningPacket()
    }

}

private object InboundHandshakePacketDeserializer : PacketDeserializer<InboundHandshakePacket> {
    override fun ByteBuf.deserialize(): InboundHandshakePacket {
        return InboundHandshakePacket()
    }
}

private object OutboundHandshakePacketSerializer : PacketSerializer<OutboundHandshakePacket> {
    override fun ByteBuf.serialize(value: OutboundHandshakePacket) {
        writeVarInt(VarInt(value.jwtData.length))
        writeBytes(value.jwtData.toByteArray())
    }
}

@Suppress("UNCHECKED_CAST")
val serializers = mapOf(
    0x03 to OutboundHandshakePacketSerializer,
    0x05 to DisconnectPacketSerializer,
) as PacketSerializerMap

@Suppress("UNCHECKED_CAST")
val deserializers = mapOf(
    0x01 to LoginPacketDeserializer,
    0x04 to InboundHandshakePacketDeserializer,
    0x9C to ViolationWarningPacketDeserializer,
) as PacketDeserializerMap
