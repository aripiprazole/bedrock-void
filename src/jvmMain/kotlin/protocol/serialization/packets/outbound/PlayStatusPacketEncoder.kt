package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object PlayStatusPacketEncoder : EncodingStrategy<PlayStatusPacket> {
  override fun EncodingStream.encodeValue(value: PlayStatusPacket) {
    encodeInt(value.status.ordinal)
  }
}
