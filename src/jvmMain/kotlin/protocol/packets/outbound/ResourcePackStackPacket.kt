package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.Serializable

@Packet(0x07)
@Serializable
data class ResourcePackStackPacket(
  val requireAccept: Boolean,
  val gameVersion: String,
  val experimentsPreviouslyToggled: Boolean,
  val experiments: List<Experiment> = emptyList(),
  val behaviorPacks: List<ResourcePack> = emptyList(),
  val resourcePacks: List<ResourcePack> = emptyList(),
) : OutboundPacket {
  @Serializable
  data class ResourcePack(
    val id: String,
    val version: String,
    val subPackName: String,
  )

  @Serializable
  data class Experiment(
    val name: String,
    val enabled: Boolean,
  )
}
