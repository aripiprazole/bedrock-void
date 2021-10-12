package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import kotlinx.serialization.Serializable

@Packet(0x9C)
@Serializable
class ViolationWarningPacket : InboundPacket
