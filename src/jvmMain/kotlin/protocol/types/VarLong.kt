package com.gabrielleeg1.bedrockvoid.protocol.types

import io.netty.buffer.ByteBuf
import kotlin.experimental.and

typealias VarLong = Long

fun ByteBuf.writeVarLong(varLong: Long): ByteBuf {
  var value = varLong

  while (true) {
    if ((value and 0xFFFFFF80) == 0L) {
      writeByte(value.toInt())
      return this
    }

    writeByte(((value and 0x7FL) or 0x80).toInt())
    value = value ushr 7
  }
}

fun ByteBuf.readVarLong(): Long {
  var offset = 0
  var value = 0L
  var byte: Byte

  do {
    if (offset == 70) error("VarLong too long")

    byte = readByte()
    value = value or ((byte.toLong() and 0x7FL) shl offset)

    offset += 7
  } while ((byte and 0x80.toByte()) != 0.toByte())

  return value
}
