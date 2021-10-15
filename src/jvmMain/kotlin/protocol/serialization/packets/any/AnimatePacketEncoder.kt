package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.AnimatePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object AnimatePacketEncoder : DecodingStrategy<AnimatePacket> {
  override fun DecodingStream.decodeT(): AnimatePacket {
    val actionId = decodeInt()
    val runtimeEntityId = decodeVarLong()

    return AnimatePacket(actionId, runtimeEntityId)
  }
}
