package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.TickSyncPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder

object TickSyncPacketEncoder : PacketEncoder<TickSyncPacket> {
  override fun EncodingStream.encodeValue(value: TickSyncPacket) {
    encodeLongLE(value.requestTimestamp)
    encodeLongLE(value.responseTimestamp)
  }

  override fun DecodingStream.decodeValue(): TickSyncPacket {
    val requestTimestamp = decodeLongLE()
    val responseTimestamp = decodeLongLE()

    return TickSyncPacket(requestTimestamp, responseTimestamp)
  }
}
