package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.TickSyncPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object TickSyncPacketEncoder : EncodingStrategy<TickSyncPacket> {
  override fun PacketEncoder.encodePacket(value: TickSyncPacket) {
    encodeLongLE(value.requestTimestamp)
    encodeLongLE(value.responseTimestamp)
  }
}
