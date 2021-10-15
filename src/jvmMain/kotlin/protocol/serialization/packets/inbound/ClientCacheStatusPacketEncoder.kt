package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ClientCacheStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import protocol.serialization.DecodingStrategy

object ClientCacheStatusPacketEncoder : DecodingStrategy<ClientCacheStatusPacket> {
  override fun DecodingStream.decodeT(): ClientCacheStatusPacket {
    return ClientCacheStatusPacket(supported = decodeBoolean())
  }
}
