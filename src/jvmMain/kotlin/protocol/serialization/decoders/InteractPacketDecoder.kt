package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.InteractPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import protocol.serialization.DecodingStrategy

object InteractPacketDecoder : DecodingStrategy<InteractPacket> {
  private fun PacketDecoder.decodeVec3(): Vec3 {
    return Vec3(decodeFloatLE(), decodeFloatLE(), decodeFloatLE())
  }

  override fun PacketDecoder.decodePacket(): InteractPacket {
    val actionId = decodeByte()
    val targetRuntimeEntityId = decodeVarLong()
    val position = decodeVec3()

    return InteractPacket(actionId, targetRuntimeEntityId, position)
  }
}
