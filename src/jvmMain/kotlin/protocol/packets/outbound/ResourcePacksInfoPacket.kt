package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack

@Packet(0x06)
data class ResourcePacksInfoPacket(
  val requireAccept: Boolean,
  val scriptingEnabled: Boolean,
  val forcingServerPacksEnabled: Boolean,
  val behaviorPackInfos: List<BehaviorPack> = emptyList(),
  val resourcePackInfos: List<ResourcePack> = emptyList(),
) : OutboundPacket
