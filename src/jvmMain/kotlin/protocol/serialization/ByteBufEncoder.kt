package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarLong
import io.netty.buffer.ByteBuf
import kotlinx.serialization.json.Json

class ByteBufEncoder(private val buf: ByteBuf, override val json: Json) : PacketEncoder {
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

  override fun encodeIntLE(value: Int) {
    buf.writeIntLE(value)
  }

  override fun encodeLong(value: Long) {
    buf.writeLong(value)
  }

  override fun encodeVarLong(value: Long) {
    buf.writeVarLong(value)
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

  override fun <T> encodeArrayShortLE(
    array: Collection<T>,
    encode: PacketEncoder.(value: T) -> Unit
  ) {
    encodeShortLE(array.size.toShort())
    array.forEach {
      encode(it)
    }
  }

  override fun <T> encodeArrayIntLE(
    array: Collection<T>,
    encode: PacketEncoder.(value: T) -> Unit
  ) {
    encodeIntLE(array.size)
    array.forEach {
      encode(it)
    }
  }

  override fun <T> encodeArray(array: Collection<T>, encode: PacketEncoder.(value: T) -> Unit) {
    encodeVarInt(array.size)
    array.forEach {
      encode(it)
    }
  }

  override fun encodeBytes(bytes: ByteArray) {
    buf.writeBytes(bytes)
  }
}
