package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus
import protocol.serialization.DecodingStrategy

object ResourcePackResponsePacketEncoder : DecodingStrategy<ResourcePackResponsePacket> {
  override fun DecodingStream.decodeT(): ResourcePackResponsePacket {
    return ResourcePackResponsePacket(
      status = ResourcePackResponseStatus.values()[decodeUByte().toInt()],
      ids = decodeArrayShortLE { decodeString() },
    )
  }
}
