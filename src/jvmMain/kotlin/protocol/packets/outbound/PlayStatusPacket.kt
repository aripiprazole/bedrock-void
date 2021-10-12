package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x02)
@Serializable
data class PlayStatusPacket(val status: Status) : OutboundPacket {
  enum class Status {
    LoginSuccess,
    FailedClient,
    FailedServer,
    PlayerSpawn,
    FailedInvalidTenant,
    FailedVanillaEdu,
    FailedEduVanilla,
    FailedServerFull;
  }
}
