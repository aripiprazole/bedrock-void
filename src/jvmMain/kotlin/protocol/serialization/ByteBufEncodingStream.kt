package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarLong
import io.netty.buffer.ByteBuf
import kotlinx.serialization.json.Json
import protocol.serialization.EncodingStrategy
import kotlin.reflect.KClass

class ByteBufEncodingStream(
  private val buf: ByteBuf,
  private val codec: EncodingCodec,
  override val json: Json,
) : EncodingStream {
  override fun encodeBoolean(value: Boolean) {
    buf.writeBoolean(value)
  }

  override fun encodeString(value: String) {
    encodeVarInt(value.length)
    encodeBytes(value.toByteArray())
  }

  override fun encodeStringLE(value: String) {
    encodeIntLE(value.length)
    encodeBytes(value.toByteArray())
  }

  override fun encodeInt(value: Int) {
    buf.writeInt(value)
  }

  override fun encodeVarInt(value: Int) {
    buf.writeVarInt(value)
  }

  override fun encodeVarUInt(value: UInt) {
    encodeVarInt((value and 0xffffffff.toUInt()).toInt())
  }

  override fun encodeIntLE(value: Int) {
    buf.writeIntLE(value)
  }

  override fun encodeLong(value: Long) {
    buf.writeLong(value)
  }

  override fun encodeVarLong(value: Long) {
    buf.writeVarLong(value)
  }

  override fun encodeVarULong(value: ULong) {
    buf.writeVarLong(value.toLong())
  }

  override fun encodeLongLE(value: Long) {
    buf.writeLongLE(value)
  }

  override fun encodeShort(value: Short) {
    buf.writeShort(value.toInt())
  }

  override fun encodeShortLE(value: Short) {
    buf.writeShortLE(value.toInt())
  }

  override fun encodeFloat(value: Float) {
    buf.writeFloat(value)
  }

  override fun encodeFloatLE(value: Float) {
    buf.writeFloatLE(value)
  }

  override fun encodeDouble(value: Double) {
    buf.writeDouble(value)
  }

  override fun encodeDoubleLE(value: Double) {
    buf.writeDoubleLE(value)
  }

  override fun <T : Any> encodeArrayShortLE(
    array: Collection<T>,
    encode: EncodingStream.(T) -> Unit,
  ) {
    encodeShortLE(array.size.toShort())
    array.forEach {
      encode(it)
    }
  }

  override fun <T : Any> encodeArrayIntLE(
    array: Collection<T>,
    encode: EncodingStream.(T) -> Unit,
  ) {
    encodeIntLE(array.size)
    array.forEach {
      encode(it)
    }
  }

  override fun <T : Any> encodeArray(array: Collection<T>, encode: EncodingStream.(T) -> Unit) {
    encodeVarUInt(array.size.toUInt())
    array.forEach {
      encode(it)
    }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> encodeValue(value: T) {
    val strategy = codec.encoders[value::class as KClass<Any>] as? EncodingStrategy<T>
      ?: error("Unknown encoding strategy for ${value::class}")

    return encodeWith(strategy, value)
  }

  override fun encodeBytes(bytes: ByteArray) {
    buf.writeBytes(bytes)
  }
}
