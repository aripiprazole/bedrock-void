package com.gabrielleeg1.bedrockvoid.protocol.types

import io.netty.buffer.ByteBuf

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
  TODO("Not yet implemented")
}
