package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import protocol.serialization.EncodingStrategy

object ResourcePacksInfoPacketEncoder : EncodingStrategy<ResourcePacksInfoPacket> {
  override fun EncodingStream.encodeT(value: ResourcePacksInfoPacket) {
    encodeBoolean(value.requireAccept)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.forcingServerPacksEnabled)
    encodeArrayShortLE(value.behaviorPackInfos)
    encodeArrayShortLE(value.resourcePackInfos)
  }
}
