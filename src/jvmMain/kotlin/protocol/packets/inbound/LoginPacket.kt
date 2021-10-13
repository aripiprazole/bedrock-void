package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.JwtData
import kotlinx.serialization.Serializable

@Packet(0x01)
@Serializable
data class LoginPacket(
  val protocolVersion: Int,
  val chainData: JwtData,
  val skinData: String
) : InboundPacket
