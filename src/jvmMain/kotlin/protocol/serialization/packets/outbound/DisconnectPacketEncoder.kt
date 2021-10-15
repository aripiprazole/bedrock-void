package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object DisconnectPacketEncoder : EncodingStrategy<DisconnectPacket> {
  override fun EncodingStream.encodeValue(value: DisconnectPacket) {
    encodeBoolean(value.hideDisconnectPacket)
    encodeString(value.kickMessage)
  }
}
