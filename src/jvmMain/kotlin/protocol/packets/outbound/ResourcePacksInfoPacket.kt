package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack
import kotlinx.serialization.Serializable

@Packet(0x06)
@Serializable
data class ResourcePacksInfoPacket(
  val requireAccept: Boolean,
  val scriptingEnabled: Boolean,
  val forcingServerPacksEnabled: Boolean,
  val behaviorPackInfos: List<BehaviorPack> = emptyList(),
  val resourcePackInfos: List<ResourcePack> = emptyList(),
) : OutboundPacket
