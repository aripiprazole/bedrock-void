package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.AnimatePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object AnimatePacketDecoder : DecodingStrategy<AnimatePacket> {
  override fun PacketDecoder.decodePacket(): AnimatePacket {
    val actionId = decodeInt()
    val runtimeEntityId = decodeVarLong()

    return AnimatePacket(actionId, runtimeEntityId)
  }
}
