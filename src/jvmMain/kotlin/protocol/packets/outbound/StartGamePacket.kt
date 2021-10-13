package com.gabrielleeg1.bedrockvoid.protocol.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockEntry
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos
import com.gabrielleeg1.bedrockvoid.protocol.types.GameRule
import com.gabrielleeg1.bedrockvoid.protocol.types.ItemEntry
import com.gabrielleeg1.bedrockvoid.protocol.types.VarInt
import com.gabrielleeg1.bedrockvoid.protocol.types.VarLong
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import kotlinx.serialization.Serializable

@Packet(0x0B)
@Serializable
data class StartGamePacket(
  val entityId: VarLong,
  val runtimeEntityId: VarLong,
  val gameMode: VarInt,
  val spawn: Vec3,
  val rotation: Vec2,
  val spawnBiomeType: Short,
  val customBiomeName: String,
  val dimension: VarInt,
  val generator: VarInt,
  val worldGameMode: VarInt,
  val difficulty: VarInt,
  val worldSpawn: BlockPos,
  val achievementsDisabled: Boolean,
  val dayCycleStopTime: VarInt,
  val educationOffer: VarInt,
  val educationFeatures: Boolean,
  val educationProductionId: String,
  val rainLevel: Float,
  val lightningLevel: Float,
  val confirmedPlatformLockedContent: Boolean,
  val isMultiplayer: Boolean,
  val broadcastToLan: Boolean,
  val xboxLiveBroadcastMode: VarInt,
  val platformBroadcastMode: VarInt,
  val commandsEnabled: Boolean,
  val requiredTexturePacks: Boolean,
  val gameRules: List<GameRule>,
  val bonusChest: Boolean,
  val startWithMapEnabled: Boolean,
  val permissionLevel: VarInt,
  val serverChunkTickRange: Int,
  val behaviorPackLocked: Boolean,
  val resourcePackLocked: Boolean,
  val msaGamerTagsOnly: Boolean,
  val fromWorldTemplate: Boolean,
  val worldTemplateOptionLocked: Boolean,
  val spawnOnlyV1Villagers: Boolean,
  val gameVersion: String,
  val limitedWorldWidth: Int,
  val limitedWorldHeight: Int,
  val netherType: Boolean,
  val forceExperimentalGameplay: Boolean,
  val levelId: String,
  val premiumWorldTemplateId: String,
  val trial: Boolean,
  val movementType: VarInt,
  val movementRewindSize: Int,
  val serverAuthoritativeBlockBreaking: Boolean,
  val currentTick: Long,
  val enchantmentSeed: VarInt,
  val blocks: List<BlockEntry>,
  val items: List<ItemEntry>,
  val multiplayerCorrelationId: String,
  val inventoriesServerAuthoritative: Boolean,
) : OutboundPacket
