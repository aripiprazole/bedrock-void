package com.gabrielleeg1.bedrockvoid.protocol.serialization

import kotlinx.serialization.json.Json

interface PacketDecoder {
  val json: Json

  fun decodeBoolean(): Boolean

  fun decodeString(): String
  fun decodeStringLE(): String

  fun decodeInt(): Int
  fun decodeVarInt(): Int
  fun decodeIntLE(): Int
  fun decodeUInt(): UInt

  fun decodeLong(): Long
  fun decodeVarLong(): Long
  fun decodeLongLE(): Long

  fun decodeShort(): Short
  fun decodeShortLE(): Short
  fun decodeUShort(): UShort

  fun decodeFloat(): Float
  fun decodeFloatLE(): Float

  fun decodeDouble(): Double
  fun decodeDoubleLE(): Double

  fun decodeByte(): Byte
  fun decodeUByte(): UByte

  fun <T> decodeArrayShortLE(decode: PacketDecoder.() -> T): List<T>
  fun <T> decodeArrayIntLE(decode: PacketDecoder.() -> T): List<T>
  fun <T> decodeArray(decode: PacketDecoder.() -> T): List<T>

  fun decodeSlice(length: Int): PacketDecoder
}
