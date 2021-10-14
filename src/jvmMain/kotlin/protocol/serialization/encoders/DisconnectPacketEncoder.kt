package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object DisconnectPacketEncoder : EncodingStrategy<DisconnectPacket> {
  override fun PacketEncoder.encodePacket(value: DisconnectPacket) {
    encodeBoolean(value.hideDisconnectPacket)
    encodeString(value.kickMessage)
  }
}
