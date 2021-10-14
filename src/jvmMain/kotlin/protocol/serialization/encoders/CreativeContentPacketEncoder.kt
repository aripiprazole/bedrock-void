package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.CreativeContentPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object CreativeContentPacketEncoder : EncodingStrategy<CreativeContentPacket> {
  override fun PacketEncoder.encodePacket(value: CreativeContentPacket) {
    encodeArray(value.content) { TODO() }
  }
}
