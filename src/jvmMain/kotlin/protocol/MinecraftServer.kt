@file:Suppress("TooGenericExceptionCaught")

package com.gabrielleeg1.bedrockvoid.protocol

import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.PacketCodec
import com.gabrielleeg1.bedrockvoid.protocol.types.PlayStatus.LoginSuccess
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
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import mu.KLogging
import java.net.InetSocketAddress

class MinecraftServer(
  private val address: InetSocketAddress,
  private val motd: MinecraftMotd,
  private val codec: PacketCodec,
  private val onSessionConnected: suspend (MinecraftSession) -> Unit = {},
  private val onSessionDisconnected: suspend (MinecraftSession) -> Unit = {},
) {
  private val sessions = HashSet<MinecraftSession>()

  private val server = RakNetServer(address).apply {
    protocolVersion = -1

    listener = object : RakNetServerListener {
      override fun onQuery(address: InetSocketAddress): ByteArray {
        return motd.copy(serverId = guid).toByteArray()
      }

      override fun onConnectionRequest(addr: InetSocketAddress, realAddr: InetSocketAddress) =
        true

      override fun onSessionCreation(connection: RakNetServerSession): Unit = runBlocking {
        val session = MinecraftSession(connection, codec)

        onSessionConnected(session)

        connection.listener = object : RakNetSessionListener {
          override fun onSessionChangeState(state: RakNetState) {
            if (state != RakNetState.CONNECTED) return

            sessions.add(session)
          }

          override fun onDisconnect(reason: DisconnectReason): Unit = runBlocking {
            logger.info { "Session disconnected from [${connection.address}]" }

            onSessionDisconnected(session)

            sessions.remove(session)
          }

          override fun onEncapsulated(packet: EncapsulatedPacket): Unit =
            runBlocking blocking@{
              if (connection.state != RakNetState.CONNECTED) return@blocking

              val buffer = packet.buffer

              if (buffer.readUnsignedByte() != 0xfe.toShort()) return@blocking
              // handle wrapped packet (wrapped packet id: 0xfe)

              try {
                session.onPacketReceived(buffer.decompress())
              } catch (error: Throwable) {
                logger.error(error) { "Failed to read game packet" }
                session.sendPacket(PlayStatusPacket(LoginSuccess))
                session.sendPacket(DisconnectPacket(kickMessage = "Failed to read game packet"))
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

  suspend fun bind() {
    logger.info { "Listening Minecraft Bedrock connections at [$address]..." }

    server.bind().await()
  }

  companion object : KLogging()
}
