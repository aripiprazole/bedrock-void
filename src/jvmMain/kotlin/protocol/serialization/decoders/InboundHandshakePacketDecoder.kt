package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object InboundHandshakePacketDecoder : DecodingStrategy<InboundHandshakePacket> {
  override fun PacketDecoder.decodePacket(): InboundHandshakePacket {
    return InboundHandshakePacket()
  }
}
