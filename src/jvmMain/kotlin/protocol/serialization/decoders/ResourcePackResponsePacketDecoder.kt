package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus
import protocol.serialization.DecodingStrategy

object ResourcePackResponsePacketDecoder : DecodingStrategy<ResourcePackResponsePacket> {
  override fun PacketDecoder.decodePacket(): ResourcePackResponsePacket {
    val status = ResourcePackResponseStatus.values()[decodeUByte().toInt()]
    val ids = decodeArrayShortLE { decodeString() }

    return ResourcePackResponsePacket(status, ids)
  }
}
