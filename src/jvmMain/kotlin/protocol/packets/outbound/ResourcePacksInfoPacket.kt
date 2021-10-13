package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x06)
@Serializable
data class ResourcePacksInfoPacket(
  val requireAccept: Boolean,
  val scriptingEnabled: Boolean,
  val forcingServerPacksEnabled: Boolean,
  val behaviorPackInfos: List<BehaviorPackInfo> = emptyList(),
  val resourcePackInfos: List<ResourcePackInfo> = emptyList(),
) : OutboundPacket {
  @Serializable
  data class BehaviorPackInfo(
    val id: String,
    val version: String,
    val size: Long,
    val contentKey: String,
    val subPackName: String,
    val contentId: String,
    val scriptingEnabled: Boolean,
  )

  @Serializable
  data class ResourcePackInfo(
    val id: String,
    val version: String,
    val size: Long,
    val contentKey: String,
    val subPackName: String,
    val contentId: String,
    val scriptingEnabled: Boolean,
    val raytracingCapable: Boolean,
  )
}
