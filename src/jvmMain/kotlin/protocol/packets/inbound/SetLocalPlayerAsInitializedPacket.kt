package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x71)
data class SetLocalPlayerAsInitializedPacket(
  val runtimeEntityId: Long,
) : InboundPacket
