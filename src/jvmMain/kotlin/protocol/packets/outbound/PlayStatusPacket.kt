package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus

@Packet(0x02)
data class PlayStatusPacket(val status: PlayStatus) : OutboundPacket
