package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.SetLocalPlayerAsInitializedPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object SetLocalPlayerAsInitializedPacketDecoder :
  DecodingStrategy<SetLocalPlayerAsInitializedPacket> {
  override fun PacketDecoder.decodePacket(): SetLocalPlayerAsInitializedPacket {
    val entityRuntimeId = decodeVarLong()

    return SetLocalPlayerAsInitializedPacket(entityRuntimeId)
  }
}
