package com.gabrielleeg1.bedrockvoid.protocol.serialization

inline fun <reified T : Any> DecodingStream.decodeValue(): T {
  return decodeValue(T::class)
}
