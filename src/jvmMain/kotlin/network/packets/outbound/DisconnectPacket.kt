package com.gabrielleeg1.bedrockvoid.network.packets.outbound

import com.gabrielleeg1.bedrockvoid.network.Packet
import com.gabrielleeg1.bedrockvoid.network.packets.OutboundPacket

@Packet(0x05)
data class DisconnectPacket(
    val hideDisconnectPacket: Boolean = false,
    val kickMessage: String,
) : OutboundPacket
