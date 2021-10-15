package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.OutboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object OutboundHandshakePacketEncoder : EncodingStrategy<OutboundHandshakePacket> {
  override fun EncodingStream.encodeT(value: OutboundHandshakePacket) {
    encodeVarInt(value.jwtData.length)
    encodeBytes(value.jwtData.toByteArray())
  }
}
