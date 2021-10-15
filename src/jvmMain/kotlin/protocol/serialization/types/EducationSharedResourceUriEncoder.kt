package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.EducationSharedResourceUri

object EducationSharedResourceUriEncoder : Encoder<EducationSharedResourceUri> {
  override fun EncodingStream.encodeT(value: EducationSharedResourceUri) {
    encodeString(value.uri)
    encodeString(value.name)
  }

  override fun DecodingStream.decodeT(): EducationSharedResourceUri {
    return EducationSharedResourceUri(decodeString(), decodeString())
  }
}
