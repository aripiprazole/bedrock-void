package com.gabrielleeg1.bedrockvoid.protocol.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.MoveMode
import com.gabrielleeg1.bedrockvoid.protocol.types.TeleportCause
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3

@Packet(0x13)
data class MovePlayerPacket(
  val runtimeEntityId: Long,
  val position: Vec3,
  val rotation: Vec3,
  val mode: MoveMode,
  val onGround: Boolean,
  val ridingRuntimeId: Long,
  val teleportCause: TeleportCause? = null,
  val teleportEntityType: Int? = null,
  val tick: Long,
) : InboundPacket, OutboundPacket
