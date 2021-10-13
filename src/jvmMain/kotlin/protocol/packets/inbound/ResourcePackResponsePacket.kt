package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import kotlinx.serialization.Serializable

@Packet(0x08)
@Serializable
data class ResourcePackResponsePacket(
  val status: Status,
  val ids: List<String>,
) : InboundPacket {
  enum class Status {
    None,
    Refused,
    SendPackets,
    HaveAllPacks,
    Completed;
  }
}
