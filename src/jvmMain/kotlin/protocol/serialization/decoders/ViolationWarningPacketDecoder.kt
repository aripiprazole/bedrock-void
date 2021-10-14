package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object ViolationWarningPacketDecoder : DecodingStrategy<ViolationWarningPacket> {
  override fun PacketDecoder.decodePacket(): ViolationWarningPacket {
    return ViolationWarningPacket()
  }
}
