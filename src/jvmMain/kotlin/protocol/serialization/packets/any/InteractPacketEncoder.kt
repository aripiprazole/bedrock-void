package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.InteractPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decodeValue
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import protocol.serialization.DecodingStrategy

object InteractPacketEncoder : DecodingStrategy<InteractPacket> {
  override fun DecodingStream.decodeT(): InteractPacket {
    val actionId = decodeByte()
    val targetRuntimeEntityId = decodeVarLong()
    val position = decodeValue<Vec3>()

    return InteractPacket(actionId, targetRuntimeEntityId, position)
  }
}
