package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object InboundHandshakePacketEncoder : DecodingStrategy<InboundHandshakePacket> {
  override fun DecodingStream.decodeT(): InboundHandshakePacket {
    return InboundHandshakePacket()
  }
}
