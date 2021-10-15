package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.RequestChunkRadiusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object RequestChunkRadiusPacketEncoder : DecodingStrategy<RequestChunkRadiusPacket> {
  override fun DecodingStream.decodeValue(): RequestChunkRadiusPacket {
    return RequestChunkRadiusPacket(decodeVarInt())
  }
}
