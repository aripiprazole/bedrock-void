package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.types.readVarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.writeVarInt
import io.netty.buffer.ByteBuf

// String
fun ByteBuf.writeString(value: String) {
  writeVarInt(value.length)
  writeBytes(value.toByteArray())
}

fun ByteBuf.readString(): String {
  return ByteArray(readVarInt())
    .also(::readBytes)
    .decodeToString()
}

fun ByteBuf.writeLEString(value: String) {
  writeIntLE(value.length)
  writeBytes(value.toByteArray())
}

fun ByteBuf.readLEString(): String {
  return ByteArray(readIntLE())
    .also(::readBytes)
    .decodeToString()
}

// Array Short LE
fun <T> ByteBuf.readArrayShortLE(read: ByteBuf.() -> T): List<T> {
  val length = readShortLE()

  return (1..length).map { read() }
}

fun <T> ByteBuf.writeArrayShortLE(array: Collection<T>, write: ByteBuf.(T) -> Unit) {
  writeShortLE(array.size)
  array.forEach {
    write(it)
  }
}

// Array Int LE
fun <T> ByteBuf.readArrayIntLE(read: ByteBuf.() -> T): List<T> {
  val length = readIntLE()

  return (1..length).map { read() }
}

fun <T> ByteBuf.writeArrayIntLE(array: Collection<T>, write: ByteBuf.(T) -> Unit) {
  writeIntLE(array.size)
  array.forEach {
    write(it)
  }
}

// Array
fun <T> ByteBuf.readArray(read: ByteBuf.() -> T): List<T> {
  return (1..readVarInt()).map { read() }
}

fun <T> ByteBuf.writeArray(array: Collection<T>, write: ByteBuf.(T) -> Unit) {
  writeVarInt(array.size)
  array.forEach {
    write(it)
  }
}

// Hex
fun Int.toHexString(): String = toUInt().toString(16)
