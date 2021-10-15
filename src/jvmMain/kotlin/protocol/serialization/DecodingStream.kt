package com.gabrielleeg1.bedrockvoid.protocol.serialization

import kotlinx.serialization.json.Json
import protocol.serialization.DecodingStrategy
import kotlin.reflect.KClass

interface DecodingStream {
  val json: Json

  fun decodeBoolean(): Boolean

  fun decodeString(): String
  fun decodeStringLE(): String

  fun decodeInt(): Int
  fun decodeVarInt(): Int
  fun decodeVarUInt(): UInt
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

  fun <T> decodeArrayShortLE(decode: DecodingStream.() -> T): List<T>
  fun <T> decodeArrayIntLE(decode: DecodingStream.() -> T): List<T>
  fun <T> decodeArray(decode: DecodingStream.() -> T): List<T>

  fun <T : Any> decodeValue(tClass: KClass<T>): T
  fun <T> decodeWith(strategy: DecodingStrategy<T>): T = strategy.run { decodeT() }

  fun decodeSlice(length: Int): DecodingStream
}
