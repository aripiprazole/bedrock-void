package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.JwtData

@Packet(0x01)
data class LoginPacket(
  val protocolVersion: Int,
  val chainData: JwtData,
  val skinData: String
) : InboundPacket
