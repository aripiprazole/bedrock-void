package com.gabrielleeg1.bedrockvoid.protocol.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3

@Packet(0x21)
data class InteractPacket(
  val actionType: Byte,
  val targetRuntimeEntityId: Long,
  val position: Vec3,
) : InboundPacket, OutboundPacket
