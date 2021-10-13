@file:Suppress("TooGenericExceptionCaught")

package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.BedrockMotd
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftServer
import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket.Status
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import java.net.InetSocketAddress
import java.util.concurrent.Executors

val json = Json {
  ignoreUnknownKeys = true
}

val ctx = CoroutineName("bedrock-void") +
  Executors.newFixedThreadPool(2).asCoroutineDispatcher()

val scope = CoroutineScope(ctx)

private val motd = BedrockMotd(
  edition = "MCPE",
  motd = "My Server",
  playerCount = 0,
  maxPlayerCount = 20,
  gameType = "Survival",
  protocolVersion = 431,
  version = "1.0"
)

private val logger = KotlinLogging.logger { }

suspend fun onSessionCreated(session: MinecraftSession) {
  session.inboundPacketFlow.onEach { packet ->
    when (packet) {
      is LoginPacket -> {
        val (_, _, displayName) = packet.chainData.chain.third.extraData

        try {
          logger.info {
            "Player $displayName with protocol [${packet.protocolVersion}] connected from [${session.address}]"
          }

          session.sendPacket(PlayStatusPacket(Status.LoginSuccess))
          session.sendPacket(DisconnectPacket(kickMessage = "Successfully logged in!"))
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

@ExperimentalStdlibApi
suspend fun main(): Unit = withContext(ctx) {
  logger.info { "Creating server..." }

  val address = InetSocketAddress("0.0.0.0", 19132)
  val server = MinecraftServer(address, motd, serializers, deserializers, ::onSessionCreated)

  server.bind()
}
