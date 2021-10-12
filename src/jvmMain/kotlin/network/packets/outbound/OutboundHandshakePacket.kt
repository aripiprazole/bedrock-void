package com.gabrielleeg1.bedrockvoid.network.packets.outbound

import com.gabrielleeg1.bedrockvoid.network.Packet
import com.gabrielleeg1.bedrockvoid.network.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x03)
@Serializable
data class OutboundHandshakePacket(val jwtData: String) : OutboundPacket
