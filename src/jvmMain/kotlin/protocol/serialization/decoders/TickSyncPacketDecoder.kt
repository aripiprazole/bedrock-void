package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.TickSyncPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object TickSyncPacketDecoder : DecodingStrategy<TickSyncPacket> {
  override fun PacketDecoder.decodePacket(): TickSyncPacket {
    val requestTimestamp = decodeLongLE()
    val responseTimestamp = decodeLongLE()

    return TickSyncPacket(requestTimestamp, responseTimestamp)
  }
}
