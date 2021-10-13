package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x91)
@Serializable
class CreativeContentPacket : OutboundPacket
