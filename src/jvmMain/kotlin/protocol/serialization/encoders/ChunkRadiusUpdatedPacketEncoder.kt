package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ChunkRadiusUpdatedPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object ChunkRadiusUpdatedPacketEncoder : EncodingStrategy<ChunkRadiusUpdatedPacket> {
  override fun PacketEncoder.encodePacket(value: ChunkRadiusUpdatedPacket) {
    encodeVarInt(value.chunkRadius)
  }
}
