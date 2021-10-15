package protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream

fun interface DecodingStrategy<T> {
  fun DecodingStream.decodeValue(): T
}
