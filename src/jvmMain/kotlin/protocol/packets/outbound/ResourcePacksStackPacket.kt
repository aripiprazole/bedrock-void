package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData
import com.gabrielleeg1.bedrockvoid.protocol.types.StackResourcePack

@Packet(0x07)
data class ResourcePacksStackPacket(
  val requireAccept: Boolean,
  val gameVersion: String,
  val experimentsPreviouslyToggled: Boolean,
  val experiments: List<ExperimentData> = emptyList(),
  val behaviorPacks: List<StackResourcePack> = emptyList(),
  val resourcePacks: List<StackResourcePack> = emptyList(),
) : OutboundPacket
