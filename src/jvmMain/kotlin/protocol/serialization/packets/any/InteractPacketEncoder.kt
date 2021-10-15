package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.InteractPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import protocol.serialization.DecodingStrategy

object InteractPacketEncoder : DecodingStrategy<InteractPacket> {
  private fun DecodingStream.decodeVec3(): Vec3 {
    return Vec3(decodeFloatLE(), decodeFloatLE(), decodeFloatLE())
  }

  override fun DecodingStream.decodeValue(): InteractPacket {
    val actionId = decodeByte()
    val targetRuntimeEntityId = decodeVarLong()
    val position = decodeVec3()

    return InteractPacket(actionId, targetRuntimeEntityId, position)
  }
}
