package com.gabrielleeg1.bedrockvoid.protocol.serialization

import kotlinx.serialization.json.Json

interface PacketEncoder {
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

  fun <T> encodeArrayShortLE(array: Collection<T>, encode: PacketEncoder.(value: T) -> Unit)
  fun <T> encodeArrayIntLE(array: Collection<T>, encode: PacketEncoder.(value: T) -> Unit)
  fun <T> encodeArray(array: Collection<T>, encode: PacketEncoder.(value: T) -> Unit)

  fun encodeBytes(bytes: ByteArray)
}
