package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.StackResourcePack

object StackResourcePackEncoder : Encoder<StackResourcePack> {
  override fun EncodingStream.encodeT(value: StackResourcePack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeString(value.subPackName)
  }

  override fun DecodingStream.decodeT(): StackResourcePack {
    return StackResourcePack(decodeString(), decodeString(), decodeString())
  }
}
