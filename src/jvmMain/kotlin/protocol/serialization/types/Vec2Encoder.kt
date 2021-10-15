package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2

object Vec2Encoder : Encoder<Vec2> {
  override fun DecodingStream.decodeT(): Vec2 {
    return Vec2(decodeFloatLE(), decodeFloatLE())
  }

  override fun EncodingStream.encodeT(value: Vec2) {
    encodeFloatLE(value.x)
    encodeFloatLE(value.y)
  }
}
