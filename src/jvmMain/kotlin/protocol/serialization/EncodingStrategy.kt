package protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream

fun interface EncodingStrategy<T> {
  fun EncodingStream.encodeValue(value: T)
}
