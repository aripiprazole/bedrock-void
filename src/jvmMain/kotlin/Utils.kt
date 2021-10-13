package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.VarInt
import com.gabrielleeg1.bedrockvoid.protocol.readVarInt
import com.gabrielleeg1.bedrockvoid.protocol.writeVarInt
import io.netty.buffer.ByteBuf

// String
fun ByteBuf.writeString(value: String) {
  writeVarInt(VarInt(value.length))
  writeBytes(value.toByteArray())
}

fun ByteBuf.readString(): String {
  val length = readVarInt()
  val bytes = ByteArray(length.value)

  readBytes(bytes)

  return bytes.decodeToString()
}

fun ByteBuf.writeLEString(value: String) {
  writeIntLE(value.length)
  writeBytes(value.toByteArray())
}

fun ByteBuf.readLEString(): String {
  val length = readIntLE()
  val data = ByteArray(length)

  readBytes(data)

  return data.decodeToString()
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
  val length = readVarInt()

  return (1..length.value).map { read() }
}

fun <T> ByteBuf.writeArray(array: Collection<T>, write: ByteBuf.(T) -> Unit) {
  writeVarInt(VarInt(array.size))
  array.forEach {
    write(it)
  }
}
