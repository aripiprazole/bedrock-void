package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.BiomeDefinitionListPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import protocol.serialization.EncodingStrategy

object BiomeDefinitionListPacketEncoder : EncodingStrategy<BiomeDefinitionListPacket> {
  override fun PacketEncoder.encodePacket(value: BiomeDefinitionListPacket) {
    encodeBytes(value.biomeDefinitions)
  }
}
