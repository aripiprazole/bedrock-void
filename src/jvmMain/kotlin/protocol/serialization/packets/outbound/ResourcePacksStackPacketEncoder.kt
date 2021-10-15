package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object ResourcePacksStackPacketEncoder : EncodingStrategy<ResourcePacksStackPacket> {
  override fun EncodingStream.encodeT(value: ResourcePacksStackPacket) {
    encodeBoolean(value.requireAccept)
    encodeArray(value.behaviorPacks)
    encodeArray(value.resourcePacks)
    encodeString(value.gameVersion)
    encodeArrayIntLE(value.experiments)
    encodeBoolean(value.experimentsPreviouslyToggled)
  }
}
