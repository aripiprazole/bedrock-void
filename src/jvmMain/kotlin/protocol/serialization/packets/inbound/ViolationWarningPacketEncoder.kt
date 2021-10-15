package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object ViolationWarningPacketEncoder : DecodingStrategy<ViolationWarningPacket> {
  override fun DecodingStream.decodeT(): ViolationWarningPacket {
    return ViolationWarningPacket()
  }
}
