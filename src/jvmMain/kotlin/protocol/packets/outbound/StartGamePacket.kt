package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockEntry
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos
import com.gabrielleeg1.bedrockvoid.protocol.types.EducationSharedResourceUri
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData
import com.gabrielleeg1.bedrockvoid.protocol.types.GameRule
import com.gabrielleeg1.bedrockvoid.protocol.types.ItemEntry
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3

@Packet(0x0B)
data class StartGamePacket(
  val entityId: Long,
  val runtimeEntityId: ULong,
  val gameMode: Int,
  val spawn: Vec3,
  val rotation: Vec2,
  val worldSeed: Int,
  val spawnBiomeType: Short,
  val customBiomeName: String,
  val dimension: Int,
  val generator: Int,
  val worldGameMode: Int,
  val difficulty: Int,
  val worldSpawn: BlockPos,
  val achievementsDisabled: Boolean,
  val dayCycleStopTime: Int,
  val educationOffer: Int,
  val educationFeatures: Boolean,
  val educationProductionId: String,
  val rainLevel: Float,
  val lightningLevel: Float,
  val confirmedPlatformLockedContent: Boolean,
  val multiplayer: Boolean,
  val broadcastToLan: Boolean,
  val xboxLiveBroadcastMode: Int,
  val platformBroadcastMode: Int,
  val commandsEnabled: Boolean,
  val requiredTexturePacks: Boolean,
  val gameRules: List<GameRule>,
  val experiments: List<ExperimentData>,
  val experimentsPreviouslyEnabled: Boolean,
  val bonusChest: Boolean,
  val startWithMapEnabled: Boolean,
  val permissionLevel: Int,
  val serverChunkTickRange: Int,
  val behaviorPackLocked: Boolean,
  val resourcePackLocked: Boolean,
  val fromWorldTemplate: Boolean,
  val msaGamerTagsOnly: Boolean,
  val fromLockedWorldTemplate: Boolean,
  val worldTemplateSettingsLocked: Boolean,
  val spawnOnlyV1Villagers: Boolean,
  val baseGameVersion: String,
  val limitedWorldWidth: Int,
  val limitedWorldHeight: Int,
  val newNether: Boolean,
  val educationSharedResourceUri: EducationSharedResourceUri,
  val forceExperimentalGameplay: Boolean,
  val levelId: String,
  val worldName: String,
  val templateContentId: String,
  val trial: Boolean,
  val movementType: Int,
  val movementRewindSize: Int,
  val serverAuthoritativeBlockBreaking: Boolean,
  val time: Long,
  val enchantmentSeed: Int,
  val blocks: List<BlockEntry>,
  val items: List<ItemEntry>,
  val multiplayerCorrelationId: String,
  val serverAuthoritativeInventory: Boolean,
  val gameVersion: String,
) : OutboundPacket
