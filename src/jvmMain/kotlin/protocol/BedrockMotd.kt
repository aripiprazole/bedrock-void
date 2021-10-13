package com.gabrielleeg1.bedrockvoid.protocol

data class BedrockMotd(
  val edition: String,
  val gameType: String,
  val protocolVersion: Int = -1,
  val motd: String,
  val subMotd: String = "",
  val nintendoLimited: Boolean = false,
  val version: String = "1.0",
  val playerCount: Int = -1,
  val maxPlayerCount: Int = -1,
  val serverId: Long = -1,
  val ipv4Port: Int = -1,
  val ipv6Port: Int = -1,
) {
  fun toByteArray(): ByteArray {
    return mutableListOf<Any>()
      .apply {
        add(edition)
        add(motd)
        add(protocolVersion)
        add(version)
        add(playerCount)
        add(maxPlayerCount)
        add(serverId)
        add(subMotd)
        add(gameType)
        add(if (nintendoLimited) "0" else "1")
        add(ipv4Port)
        add(ipv6Port)
      }
      .joinToString(separator = ";", prefix = "", postfix = ";")
      .toByteArray()
  }
}
