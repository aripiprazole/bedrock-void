package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.MovePlayerPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.types.MoveMode
import com.gabrielleeg1.bedrockvoid.protocol.types.TeleportCause
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import protocol.serialization.DecodingStrategy

object MovePlayerPacketDecoder : DecodingStrategy<MovePlayerPacket> {
  private fun PacketDecoder.decodeVec3(): Vec3 {
    return Vec3(decodeFloatLE(), decodeFloatLE(), decodeFloatLE())
  }

  override fun PacketDecoder.decodePacket(): MovePlayerPacket {
    val runtimeEntityId = decodeVarLong()
    val position = decodeVec3()
    val rotation = decodeVec3()
    val mode = MoveMode.values()[decodeByte().toInt()]
    val onGround = decodeBoolean()
    val ridingRuntimeId = decodeVarLong()
    if (mode == MoveMode.Teleport) {
      val teleportCause = TeleportCause.values()[decodeIntLE()]
      val teleportEntityType = decodeIntLE()
      val tick = decodeVarLong()

      return MovePlayerPacket(
        runtimeEntityId,
        position,
        rotation,
        mode,
        onGround,
        ridingRuntimeId,
        teleportCause,
        teleportEntityType,
        tick
      )
    }

    val tick = decodeVarLong()

    return MovePlayerPacket(
      runtimeEntityId,
      position,
      rotation,
      mode,
      onGround,
      ridingRuntimeId,
      tick = tick,
    )
  }
}
