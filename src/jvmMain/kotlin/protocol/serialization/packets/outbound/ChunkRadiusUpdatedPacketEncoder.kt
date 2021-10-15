package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ChunkRadiusUpdatedPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object ChunkRadiusUpdatedPacketEncoder : EncodingStrategy<ChunkRadiusUpdatedPacket> {
  override fun EncodingStream.encodeValue(value: ChunkRadiusUpdatedPacket) {
    encodeVarInt(value.chunkRadius)
  }
}
