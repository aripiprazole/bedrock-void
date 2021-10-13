package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.OutboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.utils.decompress
import com.nukkitx.network.raknet.EncapsulatedPacket
import com.nukkitx.network.raknet.RakNetServer
import com.nukkitx.network.raknet.RakNetServerListener
import com.nukkitx.network.raknet.RakNetServerSession
import com.nukkitx.network.raknet.RakNetSessionListener
import com.nukkitx.network.raknet.RakNetState
import com.nukkitx.network.util.DisconnectReason
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import java.net.InetSocketAddress
import java.util.concurrent.Executors

val ctx = CoroutineName("bedrock-void") +
  Executors.newFixedThreadPool(2).asCoroutineDispatcher()

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

@ExperimentalStdlibApi
@Suppress("TooGenericExceptionCaught")
suspend fun main(): Unit = withContext(ctx) {
  logger.info { "Creating server..." }

  val address = InetSocketAddress("0.0.0.0", 19132)
  val sessions = hashSetOf<MinecraftSession>()

  val server = RakNetServer(address).apply {
    protocolVersion = -1

    listener = object : RakNetServerListener {
      override fun onQuery(address: InetSocketAddress): ByteArray {
        return motd.copy(serverId = guid).toByteArray()
      }

      override fun onConnectionRequest(addr: InetSocketAddress, realAddr: InetSocketAddress) =
        true

      override fun onSessionCreation(connection: RakNetServerSession) {
        val session = MinecraftSession(connection, serializers, deserializers)

        session.inboundPacketFlow.onEach { packet ->
          when (packet) {
            is LoginPacket -> {
              logger.info {
                "Player logging-in with protocol version ${packet.protocolVersion} from [${connection.address}]"
              }

              try {
                session.sendPacket(OutboundHandshakePacket("TODO"))
              } catch (throwable: Throwable) {
                throwable.printStackTrace()
              }
            }
            is InboundHandshakePacket -> {
            }
            is ViolationWarningPacket -> {
              logger.warn { "Packet violation" }

              session.sendPacket(DisconnectPacket(kickMessage = "Packet violation"))
            }
          }
        }.launchIn(CoroutineScope(ctx))

        connection.listener = object : RakNetSessionListener {
          override fun onSessionChangeState(state: RakNetState) {
            if (state != RakNetState.CONNECTED) return

            logger.info { "Session connected from [${connection.address}]" }

            sessions.add(session)
          }

          override fun onDisconnect(reason: DisconnectReason) {
            logger.info { "Session disconnected from [${connection.address}]" }

            sessions.remove(session)
          }

          override fun onEncapsulated(packet: EncapsulatedPacket): Unit = runBlocking {
            if (connection.state != RakNetState.CONNECTED) return@runBlocking

            val buffer = packet.buffer

            if (buffer.readUnsignedByte() != 0xfe.toShort()) return@runBlocking
            // handle wrapped packet (wrapped packet id: 0xfe)

            try {
              session.onPacketReceived(buffer.decompress())
            } catch (error: Throwable) {
              error.printStackTrace()
            }
          }

          override fun onDirect(buf: ByteBuf) {
            //
          }
        }
      }

      override fun onUnhandledDatagram(ctx: ChannelHandlerContext, packet: DatagramPacket) {
        // 
      }
    }
  }

  logger.info { "Listening Minecraft Bedrock connections at [$address]..." }

  server.bind().await()
}
