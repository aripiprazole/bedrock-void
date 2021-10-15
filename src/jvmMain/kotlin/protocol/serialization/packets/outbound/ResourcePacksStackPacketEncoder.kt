package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData
import com.gabrielleeg1.bedrockvoid.protocol.types.StackResourcePack
import protocol.serialization.EncodingStrategy

object ResourcePacksStackPacketEncoder : EncodingStrategy<ResourcePacksStackPacket> {
  private fun EncodingStream.encodeResourcePack(value: StackResourcePack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeString(value.subPackName)
  }

  private fun EncodingStream.encodeExperimentData(value: ExperimentData) {
    encodeString(value.name)
    encodeBoolean(value.enabled)
  }

  override fun EncodingStream.encodeValue(value: ResourcePacksStackPacket) {
    encodeBoolean(value.requireAccept)
    encodeArray(value.behaviorPacks) { encodeResourcePack(it) }
    encodeArray(value.resourcePacks) { encodeResourcePack(it) }
    encodeString(value.gameVersion)
    encodeArrayIntLE(value.experiments) { encodeExperimentData(it) }
    encodeBoolean(value.experimentsPreviouslyToggled)
  }
}
