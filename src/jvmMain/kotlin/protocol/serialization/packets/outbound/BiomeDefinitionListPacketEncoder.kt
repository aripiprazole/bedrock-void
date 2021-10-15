package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.BiomeDefinitionListPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object BiomeDefinitionListPacketEncoder : EncodingStrategy<BiomeDefinitionListPacket> {
  override fun EncodingStream.encodeValue(value: BiomeDefinitionListPacket) {
    encodeBytes(value.biomeDefinitions)
  }
}
