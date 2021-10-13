package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import kotlinx.serialization.Serializable

@Packet(0x01)
@Serializable
data class ClientCacheStatusPacket(val supported: Boolean) : InboundPacket
