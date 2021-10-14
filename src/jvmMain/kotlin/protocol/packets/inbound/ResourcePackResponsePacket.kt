package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus

@Packet(0x08)
data class ResourcePackResponsePacket(
  val status: ResourcePackResponseStatus,
  val ids: List<String>,
) : InboundPacket
