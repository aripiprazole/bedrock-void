package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.StartGamePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos
import com.gabrielleeg1.bedrockvoid.protocol.types.EducationSharedResourceUri
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import protocol.serialization.EncodingStrategy

object StartGamePacketEncoder : EncodingStrategy<StartGamePacket> {
  private fun EncodingStream.encodeVec3(value: Vec3) {
    encodeFloatLE(value.x)
    encodeFloatLE(value.y)
    encodeFloatLE(value.z)
  }

  private fun EncodingStream.encodeVec2(value: Vec2) {
    encodeFloatLE(value.yaw)
    encodeFloatLE(value.pitch)
  }

  private fun EncodingStream.encodeBlockPos(value: BlockPos) {
    encodeVarInt(value.x)
    encodeVarUInt(value.y.toUInt())
    encodeVarInt(value.z)
  }

  private fun EncodingStream.encodeEducationSharedResourceUri(value: EducationSharedResourceUri) {
    encodeString(value.uri)
    encodeString(value.name)
  }

  @Suppress("LongMethod")
  override fun EncodingStream.encodeValue(value: StartGamePacket) {
    encodeVarLong(value.entityId)
    encodeVarULong(value.runtimeEntityId)
    encodeVarInt(value.gameMode)
    encodeVec3(value.spawn)
    encodeVec2(value.rotation)
    encodeVarInt(value.worldSeed)
    encodeShortLE(value.spawnBiomeType)
    encodeString(value.customBiomeName)
    encodeVarInt(value.dimension)
    encodeVarInt(value.generator)
    encodeVarInt(value.worldGameMode)
    encodeVarInt(value.difficulty)
    encodeBlockPos(value.worldSpawn)
    encodeBoolean(value.achievementsDisabled)
    encodeVarInt(value.dayCycleStopTime)
    encodeVarInt(value.educationOffer)
    encodeBoolean(value.educationFeatures)
    encodeString(value.educationProductionId)
    encodeFloatLE(value.rainLevel)
    encodeFloatLE(value.lightningLevel)
    encodeBoolean(value.confirmedPlatformLockedContent)
    encodeBoolean(value.multiplayer)
    encodeBoolean(value.broadcastToLan)
    encodeVarInt(value.xboxLiveBroadcastMode)
    encodeVarInt(value.platformBroadcastMode)
    encodeBoolean(value.commandsEnabled)
    encodeBoolean(value.requiredTexturePacks)
    encodeArray(value.gameRules) { TODO() }
    encodeArrayIntLE(value.experiments) {
      encodeString(it.name)
      encodeBoolean(it.enabled)
    }
    encodeBoolean(value.experimentsPreviouslyEnabled)
    encodeBoolean(value.bonusChest)
    encodeBoolean(value.startWithMapEnabled)
    encodeVarInt(value.permissionLevel)
    encodeIntLE(value.serverChunkTickRange)
    encodeBoolean(value.behaviorPackLocked)
    encodeBoolean(value.resourcePackLocked)
    encodeBoolean(value.fromLockedWorldTemplate)
    encodeBoolean(value.msaGamerTagsOnly)
    encodeBoolean(value.fromWorldTemplate)
    encodeBoolean(value.worldTemplateSettingsLocked)
    encodeBoolean(value.spawnOnlyV1Villagers)
    encodeString(value.baseGameVersion)
    encodeIntLE(value.limitedWorldHeight)
    encodeIntLE(value.limitedWorldWidth)
    encodeBoolean(value.newNether)
    encodeEducationSharedResourceUri(value.educationSharedResourceUri)
    encodeBoolean(value.forceExperimentalGameplay)
    if (value.forceExperimentalGameplay) {
      encodeBoolean(value.forceExperimentalGameplay)
    }
    encodeString(value.levelId)
    encodeString(value.worldName)
    encodeString(value.templateContentId)
    encodeBoolean(value.trial)
    encodeVarUInt(value.movementType.toUInt())
    encodeVarInt(value.movementRewindSize)
    encodeBoolean(value.serverAuthoritativeBlockBreaking)
    encodeLongLE(value.time)
    encodeVarInt(value.enchantmentSeed)
    encodeArray(value.blocks) { TODO() }
    encodeArray(value.items) { TODO() }
    encodeString(value.multiplayerCorrelationId)
    encodeBoolean(value.serverAuthoritativeInventory)
    encodeString(value.gameVersion)
  }
}
