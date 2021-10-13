package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus
import kotlinx.serialization.Serializable

@Packet(0x02)
@Serializable
data class PlayStatusPacket(val status: PlayStatus) : OutboundPacket
