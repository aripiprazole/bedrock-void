package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3

object Vec3Encoder : Encoder<Vec3> {
  override fun DecodingStream.decodeT(): Vec3 {
    return Vec3(decodeFloatLE(), decodeFloatLE(), decodeFloatLE())
  }

  override fun EncodingStream.encodeT(value: Vec3) {
    encodeFloatLE(value.x)
    encodeFloatLE(value.y)
    encodeFloatLE(value.z)
  }
}
