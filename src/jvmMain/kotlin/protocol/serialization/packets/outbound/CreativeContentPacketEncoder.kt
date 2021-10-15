package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.CreativeContentPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object CreativeContentPacketEncoder : EncodingStrategy<CreativeContentPacket> {
  override fun EncodingStream.encodeValue(value: CreativeContentPacket) {
    encodeArray(value.content) { TODO() }
  }
}
