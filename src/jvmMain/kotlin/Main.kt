@file:Suppress("TooGenericExceptionCaught", "MaximumLineLength", "MaxLineLength")

package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.MinecraftMotd
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftServer
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.any.AnimatePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.any.InteractPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.any.MovePlayerPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.any.TickSyncPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ClientCacheStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.RequestChunkRadiusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.SetLocalPlayerAsInitializedPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.BiomeDefinitionListPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ChunkRadiusUpdatedPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.CreativeContentPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.StartGamePacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingCodec
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any.AnimatePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any.InteractPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any.MovePlayerPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any.TickSyncPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.ClientCacheStatusPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.InboundHandshakePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.LoginPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.RequestChunkRadiusPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.ResourcePackResponsePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.SetLocalPlayerAsInitializedPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound.ViolationWarningPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.BiomeDefinitionListPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.ChunkRadiusUpdatedPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.CreativeContentPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.DisconnectPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.PlayStatusPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.ResourcePacksInfoPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.ResourcePacksStackPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.outbound.StartGamePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.BehaviorPackEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.BlockPosEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.EducationSharedResourceUriEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.ExperimentDataEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.ResourcePackEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.StackResourcePackEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.Vec2Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.types.Vec3Encoder
import com.gabrielleeg1.bedrockvoid.protocol.types.BehaviorPack
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos
import com.gabrielleeg1.bedrockvoid.protocol.types.EducationSharedResourceUri
import com.gabrielleeg1.bedrockvoid.protocol.types.ExperimentData
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.LoginSuccess
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.PlayerSpawn
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePack
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Completed
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.HaveAllPacks
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.None
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Refused
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.SendPackets
import com.gabrielleeg1.bedrockvoid.protocol.types.StackResourcePack
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec2
import com.gabrielleeg1.bedrockvoid.protocol.types.Vec3
import kotlinx.coroutines.CoroutineExceptionHandler
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
  CoroutineExceptionHandler { coroutineContext, throwable ->
    logger.error(throwable) { "Exception was thrown in coroutine context $coroutineContext" }
  } +
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

// TODO read from file
private const val BIOME_DEFINITION_LIST =
  "CgAKDWJhbWJvb19qdW5nbGUFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoTYmFtYm9vX2p1bmdsZV9oaWxscwUIZG93bmZhbGxmZmY/BQt0ZW1wZXJhdHVyZTMzcz8ACgViZWFjaAUIZG93bmZhbGzNzMw+BQt0ZW1wZXJhdHVyZc3MTD8ACgxiaXJjaF9mb3Jlc3QFCGRvd25mYWxsmpkZPwULdGVtcGVyYXR1cmWamRk/AAoSYmlyY2hfZm9yZXN0X2hpbGxzBQhkb3duZmFsbJqZGT8FC3RlbXBlcmF0dXJlmpkZPwAKGmJpcmNoX2ZvcmVzdF9oaWxsc19tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKFGJpcmNoX2ZvcmVzdF9tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKCmNvbGRfYmVhY2gFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw9AAoKY29sZF9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgpjb2xkX3RhaWdhBQhkb3duZmFsbM3MzD4FC3RlbXBlcmF0dXJlAAAAvwAKEGNvbGRfdGFpZ2FfaGlsbHMFCGRvd25mYWxszczMPgULdGVtcGVyYXR1cmUAAAC/AAoSY29sZF90YWlnYV9tdXRhdGVkBQhkb3duZmFsbM3MzD4FC3RlbXBlcmF0dXJlAAAAvwAKD2RlZXBfY29sZF9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8AChFkZWVwX2Zyb3plbl9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAAChNkZWVwX2x1a2V3YXJtX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKCmRlZXBfb2NlYW4FCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAA/AAoPZGVlcF93YXJtX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKBmRlc2VydAUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAACgxkZXNlcnRfaGlsbHMFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoOZGVzZXJ0X211dGF0ZWQFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoNZXh0cmVtZV9oaWxscwUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4AChJleHRyZW1lX2hpbGxzX2VkZ2UFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw+AAoVZXh0cmVtZV9oaWxsc19tdXRhdGVkBQhkb3duZmFsbJqZmT4FC3RlbXBlcmF0dXJlzcxMPgAKGGV4dHJlbWVfaGlsbHNfcGx1c190cmVlcwUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4ACiBleHRyZW1lX2hpbGxzX3BsdXNfdHJlZXNfbXV0YXRlZAUIZG93bmZhbGyamZk+BQt0ZW1wZXJhdHVyZc3MTD4ACg1mbG93ZXJfZm9yZXN0BQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKBmZvcmVzdAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzMz8ACgxmb3Jlc3RfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUzMzM/AAoMZnJvemVuX29jZWFuBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAAAAKDGZyb3plbl9yaXZlcgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAACgRoZWxsBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlAAAAQAAKDWljZV9tb3VudGFpbnMFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAAAAAoKaWNlX3BsYWlucwUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAAChFpY2VfcGxhaW5zX3NwaWtlcwUIZG93bmZhbGwAAIA/BQt0ZW1wZXJhdHVyZQAAAAAACgZqdW5nbGUFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoLanVuZ2xlX2VkZ2UFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUzM3M/AAoTanVuZ2xlX2VkZ2VfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzcz8ACgxqdW5nbGVfaGlsbHMFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoOanVuZ2xlX211dGF0ZWQFCGRvd25mYWxsZmZmPwULdGVtcGVyYXR1cmUzM3M/AAoTbGVnYWN5X2Zyb3plbl9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAAAACg5sdWtld2FybV9vY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgptZWdhX3RhaWdhBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlmpmZPgAKEG1lZ2FfdGFpZ2FfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmWamZk+AAoEbWVzYQUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAACgptZXNhX2JyeWNlBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlAAAAQAAKDG1lc2FfcGxhdGVhdQUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAAChRtZXNhX3BsYXRlYXVfbXV0YXRlZAUIZG93bmZhbGwAAAAABQt0ZW1wZXJhdHVyZQAAAEAAChJtZXNhX3BsYXRlYXVfc3RvbmUFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoabWVzYV9wbGF0ZWF1X3N0b25lX211dGF0ZWQFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAABAAAoPbXVzaHJvb21faXNsYW5kBQhkb3duZmFsbAAAgD8FC3RlbXBlcmF0dXJlZmZmPwAKFW11c2hyb29tX2lzbGFuZF9zaG9yZQUIZG93bmZhbGwAAIA/BQt0ZW1wZXJhdHVyZWZmZj8ACgVvY2VhbgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACgZwbGFpbnMFCGRvd25mYWxszczMPgULdGVtcGVyYXR1cmXNzEw/AAobcmVkd29vZF90YWlnYV9oaWxsc19tdXRhdGVkBQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlmpmZPgAKFXJlZHdvb2RfdGFpZ2FfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZQAAgD4ACgVyaXZlcgUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZQAAAD8ACg1yb29mZWRfZm9yZXN0BQhkb3duZmFsbM3MTD8FC3RlbXBlcmF0dXJlMzMzPwAKFXJvb2ZlZF9mb3Jlc3RfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZTMzMz8ACgdzYXZhbm5hBQhkb3duZmFsbAAAAAAFC3RlbXBlcmF0dXJlmpmZPwAKD3NhdmFubmFfbXV0YXRlZAUIZG93bmZhbGwAAAA/BQt0ZW1wZXJhdHVyZc3MjD8ACg9zYXZhbm5hX3BsYXRlYXUFCGRvd25mYWxsAAAAAAULdGVtcGVyYXR1cmUAAIA/AAoXc2F2YW5uYV9wbGF0ZWF1X211dGF0ZWQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAIA/AAoLc3RvbmVfYmVhY2gFCGRvd25mYWxsmpmZPgULdGVtcGVyYXR1cmXNzEw+AAoQc3VuZmxvd2VyX3BsYWlucwUIZG93bmZhbGzNzMw+BQt0ZW1wZXJhdHVyZc3MTD8ACglzd2FtcGxhbmQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmXNzEw/AAoRc3dhbXBsYW5kX211dGF0ZWQFCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmXNzEw/AAoFdGFpZ2EFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUAAIA+AAoLdGFpZ2FfaGlsbHMFCGRvd25mYWxszcxMPwULdGVtcGVyYXR1cmUAAIA+AAoNdGFpZ2FfbXV0YXRlZAUIZG93bmZhbGzNzEw/BQt0ZW1wZXJhdHVyZQAAgD4ACgd0aGVfZW5kBQhkb3duZmFsbAAAAD8FC3RlbXBlcmF0dXJlAAAAPwAKCndhcm1fb2NlYW4FCGRvd25mYWxsAAAAPwULdGVtcGVyYXR1cmUAAAA/AAA="

@ExperimentalStdlibApi
suspend fun main(): Unit = withContext(ctx) {
  logger.info { "Creating server..." }

  val codec = EncodingCodec(
    json = Json {
      ignoreUnknownKeys = true
    },
    strategies = buildMap {
      put(LoginPacket::class, LoginPacketEncoder)
      put(InboundHandshakePacket::class, InboundHandshakePacketEncoder)
      put(ResourcePackResponsePacket::class, ResourcePackResponsePacketEncoder)
      put(ResourcePacksInfoPacket::class, ResourcePacksInfoPacketEncoder)
      put(ResourcePacksStackPacket::class, ResourcePacksStackPacketEncoder)
      put(ClientCacheStatusPacket::class, ClientCacheStatusPacketEncoder)
      put(ViolationWarningPacket::class, ViolationWarningPacketEncoder)
      put(RequestChunkRadiusPacket::class, RequestChunkRadiusPacketEncoder)
      put(TickSyncPacket::class, TickSyncPacketEncoder)
      put(MovePlayerPacket::class, MovePlayerPacketEncoder)
      put(InteractPacket::class, InteractPacketEncoder)
      put(SetLocalPlayerAsInitializedPacket::class, SetLocalPlayerAsInitializedPacketEncoder)
      put(AnimatePacket::class, AnimatePacketEncoder)
      put(PlayStatusPacket::class, PlayStatusPacketEncoder)
      put(DisconnectPacket::class, DisconnectPacketEncoder)
      put(StartGamePacket::class, StartGamePacketEncoder)
      put(ChunkRadiusUpdatedPacket::class, ChunkRadiusUpdatedPacketEncoder)
      put(CreativeContentPacket::class, CreativeContentPacketEncoder)
      put(BiomeDefinitionListPacket::class, BiomeDefinitionListPacketEncoder)
      put(Vec3::class, Vec3Encoder)
      put(Vec2::class, Vec2Encoder)
      put(StackResourcePack::class, StackResourcePackEncoder)
      put(ResourcePack::class, ResourcePackEncoder)
      put(ExperimentData::class, ExperimentDataEncoder)
      put(EducationSharedResourceUri::class, EducationSharedResourceUriEncoder)
      put(BlockPos::class, BlockPosEncoder)
      put(BehaviorPack::class, BehaviorPackEncoder)
    },
  )

  val address = InetSocketAddress("0.0.0.0", 19132)
  val server = MinecraftServer(address, motd, codec, ::onSessionCreated)

  server.bind()
}

fun onSessionCreated(session: MinecraftSession) {
  handleLogin(session)
  handleResourcePack(session)
  handlePlayerPackets(session)
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

        session.disconnect("Packet violation")
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
      Completed -> session.sendPacket(
        StartGamePacket(
          entityId = Random.nextLong(),
          runtimeEntityId = Random.nextULong(),
          gameMode = 1, // Creative
          spawn = Vec3(x = 0f, y = 0f, z = 0f),
          rotation = Vec2(x = 0f, y = 0f),
          worldSeed = Random.nextInt(),
          spawnBiomeType = Random.nextInt().toShort(),
          customBiomeName = "bamboo_jungle",
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
    }
  }.launchIn(scope)
}

fun handlePlayerPackets(session: MinecraftSession) {
  var initialized = false
  val biomeDefinitionListNbt = Base64.getDecoder().decode(BIOME_DEFINITION_LIST.toByteArray())

  session.inboundPacketFlow.onEach { packet ->
    when (packet) {
      is RequestChunkRadiusPacket -> {
        session.sendPacket(ChunkRadiusUpdatedPacket(packet.chunkRadius))

        if (!initialized) {
          session.sendPacket(BiomeDefinitionListPacket(biomeDefinitionListNbt))
          session.sendPacket(CreativeContentPacket())
          session.sendPacket(PlayStatusPacket(PlayerSpawn))
          initialized = true
        }
      }
      is TickSyncPacket -> {
      }
    }
  }.launchIn(scope)
}
