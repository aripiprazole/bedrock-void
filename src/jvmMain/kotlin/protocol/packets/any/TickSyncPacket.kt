package com.gabrielleeg1.bedrockvoid.protocol.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet

@Packet(0x17)
class TickSyncPacket(
  val requestTimestamp: Long,
  val responseTimestamp: Long,
) : InboundPacket, OutboundPacket
