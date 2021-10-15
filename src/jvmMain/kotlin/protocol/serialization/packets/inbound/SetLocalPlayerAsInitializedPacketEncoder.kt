package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.SetLocalPlayerAsInitializedPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object SetLocalPlayerAsInitializedPacketEncoder :
  DecodingStrategy<SetLocalPlayerAsInitializedPacket> {
  override fun DecodingStream.decodeT(): SetLocalPlayerAsInitializedPacket {
    return SetLocalPlayerAsInitializedPacket(runtimeEntityId = decodeVarLong())
  }
}
