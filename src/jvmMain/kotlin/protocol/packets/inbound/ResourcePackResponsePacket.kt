package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus
import kotlinx.serialization.Serializable

@Packet(0x08)
@Serializable
data class ResourcePackResponsePacket(
  val status: ResourcePackResponseStatus,
  val ids: List<String>,
) : InboundPacket
