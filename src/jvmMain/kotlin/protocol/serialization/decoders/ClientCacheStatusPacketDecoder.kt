package com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ClientCacheStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketDecoder
import protocol.serialization.DecodingStrategy

object ClientCacheStatusPacketDecoder : DecodingStrategy<ClientCacheStatusPacket> {
  override fun PacketDecoder.decodePacket(): ClientCacheStatusPacket {
    return ClientCacheStatusPacket(decodeBoolean())
  }
}
