package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData
import com.gabrielleeg1.bedrockvoid.protocol.types.StackResourcePack
import kotlinx.serialization.Serializable

@Packet(0x07)
@Serializable
data class ResourcePackStackPacket(
  val requireAccept: Boolean,
  val gameVersion: String,
  val experimentsPreviouslyToggled: Boolean,
  val experiments: List<ExperimentData> = emptyList(),
  val behaviorPacks: List<StackResourcePack> = emptyList(),
  val resourcePacks: List<StackResourcePack> = emptyList(),
) : OutboundPacket
