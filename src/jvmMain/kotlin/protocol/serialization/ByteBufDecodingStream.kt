package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.types.readVarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.readVarLong
import io.netty.buffer.ByteBuf
import kotlinx.serialization.json.Json
import protocol.serialization.DecodingStrategy
import kotlin.reflect.KClass

class ByteBufDecodingStream(
  private val buf: ByteBuf,
  private val codec: EncodingCodec,
  override val json: Json,
) : DecodingStream {
  override fun decodeBoolean(): Boolean = buf.readBoolean()
  override fun decodeInt(): Int = buf.readInt()
  override fun decodeVarInt(): Int = buf.readVarInt()
  override fun decodeVarUInt(): UInt = buf.readVarInt().toUInt()
  override fun decodeIntLE(): Int = buf.readIntLE()
  override fun decodeUInt(): UInt = buf.readUnsignedInt().toUInt()
  override fun decodeLong(): Long = buf.readLong()
  override fun decodeVarLong(): Long = buf.readVarLong()
  override fun decodeLongLE(): Long = buf.readLongLE()
  override fun decodeShort(): Short = buf.readShort()
  override fun decodeShortLE(): Short = buf.readShortLE()
  override fun decodeUShort(): UShort = buf.readUnsignedShort().toUShort()
  override fun decodeFloat(): Float = buf.readFloat()
  override fun decodeFloatLE(): Float = buf.readFloatLE()
  override fun decodeDouble(): Double = buf.readDouble()
  override fun decodeDoubleLE(): Double = buf.readDoubleLE()
  override fun decodeByte(): Byte = buf.readByte()
  override fun decodeUByte(): UByte = buf.readUnsignedByte().toUByte()

  override fun decodeString(): String {
    return ByteArray(decodeVarInt())
      .also(buf::readBytes)
      .decodeToString()
  }

  override fun decodeStringLE(): String {
    return ByteArray(decodeIntLE())
      .also(buf::readBytes)
      .decodeToString()
  }

  override fun <T> decodeArrayShortLE(decode: DecodingStream.() -> T): List<T> {
    return (1..decodeShortLE()).map { decode() }
  }

  override fun <T> decodeArrayIntLE(decode: DecodingStream.() -> T): List<T> {
    return (1..decodeIntLE()).map { decode() }
  }

  override fun <T> decodeArray(decode: DecodingStream.() -> T): List<T> {
    return (1..decodeVarInt()).map { decode() }
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : Any> decodeValue(tClass: KClass<T>): T {
    val strategy = codec.decoders[tClass as KClass<Any>] as? DecodingStrategy<T>
      ?: error("Unknown decoding strategy for $tClass")

    return decodeWith(strategy)
  }

  override fun decodeSlice(length: Int): DecodingStream =
    ByteBufDecodingStream(buf.readSlice(length), codec, json)
}
