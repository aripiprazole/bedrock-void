package com.gabrielleeg1.bedrockvoid.network.packets.inbound

import com.gabrielleeg1.bedrockvoid.network.Packet
import com.gabrielleeg1.bedrockvoid.network.packets.InboundPacket
import kotlinx.serialization.Serializable

@Packet(0x01)
@Serializable
data class LoginPacket(
  val protocolVersion: Int,
  val chainData: String,
  val skinData: String
) : InboundPacket
