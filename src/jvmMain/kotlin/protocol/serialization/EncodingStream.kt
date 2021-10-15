package com.gabrielleeg1.bedrockvoid.protocol.serialization

import kotlinx.serialization.json.Json
import protocol.serialization.EncodingStrategy

interface EncodingStream {
  val json: Json

  fun encodeBoolean(value: Boolean)

  fun encodeString(value: String)
  fun encodeStringLE(value: String)

  fun encodeInt(value: Int)
  fun encodeVarInt(value: Int)
  fun encodeVarUInt(value: UInt)
  fun encodeIntLE(value: Int)

  fun encodeLong(value: Long)
  fun encodeVarLong(value: Long)
  fun encodeVarULong(value: ULong)
  fun encodeLongLE(value: Long)

  fun encodeShort(value: Short)
  fun encodeShortLE(value: Short)

  fun encodeFloat(value: Float)
  fun encodeFloatLE(value: Float)

  fun encodeDouble(value: Double)
  fun encodeDoubleLE(value: Double)

  fun <T : Any> encodeArrayShortLE(
    array: Collection<T>,
    encode: EncodingStream.(T) -> Unit = { encodeValue(it) },
  )

  fun <T : Any> encodeArrayIntLE(
    array: Collection<T>,
    encode: EncodingStream.(T) -> Unit = { encodeValue(it) },
  )

  fun <T : Any> encodeArray(
    array: Collection<T>,
    encode: EncodingStream.(T) -> Unit = { encodeValue(it) },
  )

  fun <T : Any> encodeValue(value: T)
  fun <T : Any> encodeWith(strategy: EncodingStrategy<T>, value: T): Unit = strategy.run {
    encodeT(value)
  }

  fun encodeBytes(bytes: ByteArray)
}
