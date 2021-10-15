package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.MovePlayerPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decodeValue
import com.gabrielleeg1.bedrockvoid.protocol.types.MoveMode
import com.gabrielleeg1.bedrockvoid.protocol.types.TeleportCause
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3

object MovePlayerPacketEncoder : Encoder<MovePlayerPacket> {
  override fun EncodingStream.encodeT(value: MovePlayerPacket) {
    TODO("Not yet implemented")
  }

  override fun DecodingStream.decodeT(): MovePlayerPacket {
    val runtimeEntityId = decodeVarLong()
    val position = decodeValue<Vec3>()
    val rotation = decodeValue<Vec3>()
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
