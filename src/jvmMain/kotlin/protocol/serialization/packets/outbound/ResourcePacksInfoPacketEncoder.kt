package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack
import protocol.serialization.EncodingStrategy

object ResourcePacksInfoPacketEncoder : EncodingStrategy<ResourcePacksInfoPacket> {
  private fun EncodingStream.encodeBehaviorPack(value: BehaviorPack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
  }

  private fun EncodingStream.encodeResourcePack(value: ResourcePack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.raytracingCapable)
  }

  override fun EncodingStream.encodeValue(value: ResourcePacksInfoPacket) {
    encodeBoolean(value.requireAccept)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.forcingServerPacksEnabled)
    encodeArrayShortLE(value.behaviorPackInfos) { encodeBehaviorPack(it) }
    encodeArrayShortLE(value.resourcePackInfos) { encodeResourcePack(it) }
  }
}
