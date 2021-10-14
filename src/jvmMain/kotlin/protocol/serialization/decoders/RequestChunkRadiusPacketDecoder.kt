package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.RequestChunkRadiusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object RequestChunkRadiusPacketDecoder : DecodingStrategy<RequestChunkRadiusPacket> {
  override fun PacketDecoder.decodePacket(): RequestChunkRadiusPacket {
    return RequestChunkRadiusPacket(decodeVarInt())
  }
}
