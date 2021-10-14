package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x46)
class ChunkRadiusUpdatedPacket(val chunkRadius: Int) : OutboundPacket
