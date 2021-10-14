package protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder

fun interface DecodingStrategy<T : InboundPacket> {
  fun PacketDecoder.decodePacket(): T
}
