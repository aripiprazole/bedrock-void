package protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketEncoder

fun interface EncodingStrategy<T : OutboundPacket> {
  fun PacketEncoder.encodePacket(value: T)
}
