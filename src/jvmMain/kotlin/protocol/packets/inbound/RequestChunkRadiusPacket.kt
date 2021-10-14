package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x45)
class RequestChunkRadiusPacket(val chunkRadius: Int) : InboundPacket
