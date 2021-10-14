package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x03)
data class OutboundHandshakePacket(val jwtData: String) : OutboundPacket
