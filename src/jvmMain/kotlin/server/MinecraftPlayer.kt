package com.gabrielleeg1.bedrockvoid.server

import com.gabrielleeg1.bedrockvoid.protocol.MinecraftSession
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket

data class MinecraftPlayer(
  val username: String,
  val session: MinecraftSession,
  val version: Int,
  val state: PlayerState = PlayerState.Connected,
) {
  val address by session::address

  suspend fun disconnect(reason: String) {
    sendPackets(DisconnectPacket(kickMessage = reason))
  }

  suspend fun sendPackets(vararg packets: OutboundPacket) {
    session.sendPackets(packets.toList())
  }
}

enum class PlayerState {
  Joining, InGame, Connected;
}
