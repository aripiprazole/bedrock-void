@file:Suppress("TooGenericExceptionCaught")

package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.MinecraftMotd
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftServer
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.utils.withPacketId
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketCodec
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ClientCacheStatusPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.InboundHandshakePacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.LoginPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ResourcePackResponsePacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.decoders.ViolationWarningPacketDecoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.DisconnectPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.OutboundHandshakePacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.PlayStatusPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.ResourcePacksInfoPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.encoders.ResourcePacksStackPacketEncoder
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.LoginSuccess
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Completed
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.HaveAllPacks
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.None
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.Refused
import com.gabrielleeg1.bedrockvoid.protocol.types.ResourcePackResponseStatus.SendPackets
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
import java.util.concurrent.Executors

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
    ),
    outboundPackets = mapOf(
      withPacketId(PlayStatusPacketEncoder),
      withPacketId(OutboundHandshakePacketEncoder),
      withPacketId(DisconnectPacketEncoder),
      withPacketId(ResourcePacksInfoPacketEncoder),
      withPacketId(ResourcePacksStackPacketEncoder),
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
      }
    }
  }.launchIn(scope)
}
