@file:Suppress("TooGenericExceptionCaught")

package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.MinecraftMotd
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftServer
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.RequestChunkRadiusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.BiomeDefinitionListPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ChunkRadiusUpdatedPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.CreativeContentPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.StartGamePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.utils.withPacketId
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketCodec
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ClientCacheStatusPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.InboundHandshakePacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.LoginPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.RequestChunkRadiusPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ResourcePackResponsePacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.TickSyncPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ViolationWarningPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.BiomeDefinitionListPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.ChunkRadiusUpdatedPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.CreativeContentPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.DisconnectPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.OutboundHandshakePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.PlayStatusPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.ResourcePacksInfoPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.ResourcePacksStackPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.StartGamePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos
import com.gabrielleeg1.bedrockvoid.protocol.types.EducationSharedResourceUri
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.LoginSuccess
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.PlayerSpawn
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Completed
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.HaveAllPacks
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.None
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Refused
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.SendPackets
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.net.InetSocketAddress
import java.util.Base64
import java.util.UUID
import java.util.concurrent.Executors
import kotlin.random.Random
import kotlin.random.nextULong

private val logger = KotlinLogging.logger { }

private val ctx = CoroutineName("bedrock-void") +
  Executors.newFixedThreadPool(2).asCoroutineDispatcher()

private val scope = CoroutineScope(ctx)

private val motd = MinecraftMotd(
  edition = "MCPE",
  motd = "My Server",
  playerCount = 0,
  maxPlayerCount = 20,
  gameType = "Survival",
  protocolVersion = 465,
  version = "1.0",
)

@ExperimentalStdlibApi
suspend fun main(): Unit = withContext(ctx) {
  logger.info { "Creating server..." }

  val codec = PacketCodec(
    json = Json {
      ignoreUnknownKeys = true
    },
    inboundPackets = mapOf(
      withPacketId(LoginPacketDecoder),
      withPacketId(InboundHandshakePacketDecoder),
      withPacketId(ResourcePackResponsePacketDecoder),
      withPacketId(ClientCacheStatusPacketDecoder),
      withPacketId(ViolationWarningPacketDecoder),
      withPacketId(RequestChunkRadiusPacketDecoder),
      withPacketId(TickSyncPacketDecoder),
    ),
    outboundPackets = mapOf(
      withPacketId(PlayStatusPacketEncoder),
      withPacketId(OutboundHandshakePacketEncoder),
      withPacketId(DisconnectPacketEncoder),
      withPacketId(ResourcePacksInfoPacketEncoder),
      withPacketId(ResourcePacksStackPacketEncoder),
      withPacketId(StartGamePacketEncoder),
      withPacketId(ChunkRadiusUpdatedPacketEncoder),
      withPacketId(CreativeContentPacketEncoder),
      withPacketId(BiomeDefinitionListPacketEncoder),
    ),
  )

  val address = InetSocketAddress("0.0.0.0", 19132)
  val server = MinecraftServer(address, motd, codec, ::onSessionCreated)

  server.bind()
}

fun onSessionCreated(session: MinecraftSession) {
  handleLogin(session)
  handleResourcePack(session)
}

fun handleLogin(session: MinecraftSession) {
  session.inboundPacketFlow.onEach { packet ->
    when (packet) {
      is LoginPacket -> {
        val (_, _, displayName) = packet.chainData.chain.third.extraData

        try {
          logger.info {
            "Player $displayName with protocol [${packet.protocolVersion}] connected from [${session.address}]"
          }

          session.sendPacket(PlayStatusPacket(LoginSuccess))

          session.sendPacket(
            ResourcePacksInfoPacket(
              requireAccept = false,
              scriptingEnabled = false,
              forcingServerPacksEnabled = false,
            ),
          )
        } catch (error: Throwable) {
          error.printStackTrace()
        }
      }
      is ViolationWarningPacket -> {
        logger.warn { "Packet violation" }

        session.sendPacket(DisconnectPacket(kickMessage = "Packet violation"))
      }
    }
  }.launchIn(scope)
}

@Suppress("LongMethod")
fun handleResourcePack(session: MinecraftSession) {
  session.inboundPacketFlow.filterIsInstance<ResourcePackResponsePacket>().onEach { packet ->
    when (packet.status) {
      None -> session.disconnect("TODO: None")
      Refused -> session.disconnect("TODO: Refused")
      SendPackets -> session.disconnect("TODO: HaveAllPacks")
      HaveAllPacks -> session.sendPacket(
        ResourcePacksStackPacket(
          requireAccept = false,
          gameVersion = "1.17.30",
          experimentsPreviouslyToggled = false,
        ),
      )
      Completed -> {
        session.sendPacket(
          StartGamePacket(
            entityId = Random.nextLong(),
            runtimeEntityId = Random.nextULong(),
            gameMode = 1, // Creative
            spawn = Vec3(x = 0f, y = 0f, z = 0f),
            rotation = Vec2(yaw = 0f, pitch = 0f),
            worldSeed = Random.nextInt(),
            spawnBiomeType = Random.nextInt().toShort(),
            customBiomeName = "Plains",
            dimension = 0, // overworld
            generator = 2, // flat world
            worldGameMode = 5, // default game mode
            difficulty = 0, // peaceful
            worldSpawn = BlockPos(x = 0, y = 0, z = 0),
            achievementsDisabled = true,
            dayCycleStopTime = 0,
            educationOffer = 0, // off
            educationFeatures = false,
            educationProductionId = "",
            rainLevel = 0f, // off
            lightningLevel = 0f, // off
            confirmedPlatformLockedContent = true,
            multiplayer = true,
            broadcastToLan = false,
            xboxLiveBroadcastMode = 0, // off
            platformBroadcastMode = 0, // off
            commandsEnabled = true,
            requiredTexturePacks = false,
            gameRules = emptyList(),
            experiments = emptyList(),
            experimentsPreviouslyEnabled = false,
            bonusChest = false,
            startWithMapEnabled = false,
            permissionLevel = 2, // operator
            serverChunkTickRange = 20,
            behaviorPackLocked = false,
            resourcePackLocked = false,
            fromLockedWorldTemplate = false,
            msaGamerTagsOnly = false,
            fromWorldTemplate = false,
            worldTemplateSettingsLocked = true,
            spawnOnlyV1Villagers = false,
            baseGameVersion = "1.17.30",
            limitedWorldHeight = 0,
            limitedWorldWidth = 0,
            newNether = true, // new 1.16 nether
            educationSharedResourceUri = EducationSharedResourceUri(name = "", uri = ""),
            forceExperimentalGameplay = false,
            levelId = Base64.getEncoder().encodeToString("world".toByteArray()),
            worldName = "world",
            templateContentId = "",
            trial = false,
            movementType = 0,
            movementRewindSize = 0,
            serverAuthoritativeBlockBreaking = false,
            time = 0,
            enchantmentSeed = Random.nextInt(),
            blocks = emptyList(),
            items = emptyList(),
            multiplayerCorrelationId = UUID.randomUUID().toString(),
            serverAuthoritativeInventory = false,
            gameVersion = "1.17.30"
          ),
        )
        session.sendPacket(CreativeContentPacket())
        session.sendPacket(BiomeDefinitionListPacket())
        session.sendPacket(PlayStatusPacket(PlayerSpawn))
      }
    }
  }.launchIn(scope)
}

fun handlePlayerPackets(session: MinecraftSession) {
  session.inboundPacketFlow.onEach { packet ->
    when (packet) {
      is RequestChunkRadiusPacket -> session.sendPacket(ChunkRadiusUpdatedPacket(packet.chunkRadius))
    }
  }.launchIn(scope)
}
