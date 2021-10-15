package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.TickSyncPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream

object TickSyncPacketEncoder : Encoder<TickSyncPacket> {
  override fun EncodingStream.encodeT(value: TickSyncPacket) {
    encodeLongLE(value.requestTimestamp)
    encodeLongLE(value.responseTimestamp)
  }

  override fun DecodingStream.decodeT(): TickSyncPacket {
    val requestTimestamp = decodeLongLE()
    val responseTimestamp = decodeLongLE()

    return TickSyncPacket(requestTimestamp, responseTimestamp)
  }
}
