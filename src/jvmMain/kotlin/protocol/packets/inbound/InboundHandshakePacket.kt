package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.network.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import kotlinx.serialization.Serializable

@Packet(0x04)
@Serializable
class InboundHandshakePacket : InboundPacket
