package com.gabrielleeg1.bedrockvoid.protocol.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x2c)
data class AnimatePacket(
  val actionId: Int,
  val runtimeEntityId: Long,
) : InboundPacket, OutboundPacket
