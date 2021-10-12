package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x03)
@Serializable
data class OutboundHandshakePacket(val jwtData: String) : OutboundPacket
