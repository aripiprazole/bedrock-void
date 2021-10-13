package com.gabrielleeg1.bedrockvoid.protocol.types

import io.netty.buffer.ByteBuf
import kotlin.experimental.and

typealias VarInt = Int

fun ByteBuf.writeVarInt(varInt: VarInt) {
  var value = varInt

  while (true) {
    if ((value and 0xFFFFFF80.toInt()) == 0) {
      writeByte(value)
      return
    }

    writeByte((value and 0x7F) or 0x80)
    value = value ushr 7
  }
}

fun ByteBuf.readVarInt(): VarInt {
  var offset = 0
  var value = 0L
  var byte: Byte

  do {
    if (offset == 35) error("VarInt too long")

    byte = readByte()
    value = value or ((byte.toLong() and 0x7FL) shl offset)

    offset += 7
  } while ((byte and 0x80.toByte()) != 0.toByte())

  return value.toInt()
}
