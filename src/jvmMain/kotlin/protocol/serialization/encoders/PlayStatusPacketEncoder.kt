package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object PlayStatusPacketEncoder : EncodingStrategy<PlayStatusPacket> {
  override fun PacketEncoder.encodePacket(value: PlayStatusPacket) {
    encodeInt(value.status.ordinal)
  }
}
