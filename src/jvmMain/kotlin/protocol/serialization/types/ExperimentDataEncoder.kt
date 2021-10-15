package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData

object ExperimentDataEncoder : Encoder<ExperimentData> {
  override fun EncodingStream.encodeT(value: ExperimentData) {
    encodeString(value.name)
    encodeBoolean(value.enabled)
  }

  override fun DecodingStream.decodeT(): ExperimentData {
    return ExperimentData(decodeString(), decodeBoolean())
  }
}
