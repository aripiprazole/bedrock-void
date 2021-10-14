package com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack
import protocol.serialization.EncodingStrategy

object ResourcePacksInfoPacketEncoder : EncodingStrategy<ResourcePacksInfoPacket> {
  private fun PacketEncoder.encodeBehaviorPack(value: BehaviorPack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
  }

  private fun PacketEncoder.encodeResourcePack(value: ResourcePack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.raytracingCapable)
  }

  override fun PacketEncoder.encodePacket(value: ResourcePacksInfoPacket) {
    encodeBoolean(value.requireAccept)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.forcingServerPacksEnabled)
    encodeArrayShortLE(value.behaviorPackInfos) { encodeBehaviorPack(it) }
    encodeArrayShortLE(value.resourcePackInfos) { encodeResourcePack(it) }
  }
}
