package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack

object BehaviorPackEncoder : Encoder<BehaviorPack> {
  override fun EncodingStream.encodeT(value: BehaviorPack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
  }

  override fun DecodingStream.decodeT(): BehaviorPack {
    return BehaviorPack(
      id = decodeString(),
      version = decodeString(),
      size = decodeLongLE(),
      contentKey = decodeString(),
      subPackName = decodeString(),
      contentId = decodeString(),
      scriptingEnabled = decodeBoolean(),
    )
  }
}
