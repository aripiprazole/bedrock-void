package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack

object ResourcePackEncoder : Encoder<ResourcePack> {
  override fun EncodingStream.encodeT(value: ResourcePack) {
    encodeString(value.id)
    encodeString(value.version)
    encodeLongLE(value.size)
    encodeString(value.contentKey)
    encodeString(value.subPackName)
    encodeString(value.contentId)
    encodeBoolean(value.scriptingEnabled)
    encodeBoolean(value.raytracingCapable)
  }

  override fun DecodingStream.decodeT(): ResourcePack {
    return ResourcePack(
      id = decodeString(),
      version = decodeString(),
      size = decodeLongLE(),
      contentKey = decodeString(),
      subPackName = decodeString(),
      contentId = decodeString(),
      scriptingEnabled = decodeBoolean(),
      raytracingCapable = decodeBoolean(),
    )
  }
}
