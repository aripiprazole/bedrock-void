package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.OutboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object OutboundHandshakePacketEncoder : EncodingStrategy<OutboundHandshakePacket> {
  override fun PacketEncoder.encodePacket(value: OutboundHandshakePacket) {
    encodeVarInt(value.jwtData.length)
    encodeBytes(value.jwtData.toByteArray())
  }
}
