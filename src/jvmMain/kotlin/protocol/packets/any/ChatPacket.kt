package com.gabrielleeg1.bedrockvoid.protocol.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import com.gabrielleeg1.bedrockvoid.protocol.types.ChatType

@Packet(0x09)
data class ChatPacket(
  val type: ChatType,
  val needsTranslation: Boolean,
  val sourceName: String? = null,
  val message: String,
  val parameters: List<String> = emptyList(),
  val xuid: String = "",
  val platformChatId: String = "",
) : InboundPacket, OutboundPacket
