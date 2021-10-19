package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.any

import com.gabrielleeg1.bedrockvoid.protocol.packets.any.ChatPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.ChatType

object ChatPacketEncoder : Encoder<ChatPacket> {
  override fun DecodingStream.decodeT(): ChatPacket {
    val type = ChatType.values()[decodeUByte().toInt()]
    val needsTranslation = decodeBoolean()

    return when (type) {
      ChatType.Chat,
      ChatType.Announcement,
      ChatType.Whisper -> {
        val sourceName = decodeString()
        val message = decodeString()
        val xuid = decodeString()
        val platformChatId = decodeString()

        ChatPacket(type, needsTranslation, sourceName, message, emptyList(), xuid, platformChatId)
      }
      ChatType.Raw,
      ChatType.Object,
      ChatType.ObjectWhisper,
      ChatType.System,
      ChatType.Tip -> {
        val sourceName = decodeString()
        val message = decodeString()
        val xuid = decodeString()
        val platformChatId = decodeString()

        ChatPacket(type, needsTranslation, sourceName, message, emptyList(), xuid, platformChatId)
      }
      ChatType.Translation,
      ChatType.JukeboxPopup,
      ChatType.Popup -> {
        val message = decodeString()
        val parameters = decodeArray { decodeString() }
        val xuid = decodeString()
        val platformChatId = decodeString()

        ChatPacket(type, needsTranslation, null, message, parameters, xuid, platformChatId)
      }
    }
  }

  override fun EncodingStream.encodeT(value: ChatPacket) {
    encodeByte(value.type.ordinal.toByte())
    encodeBoolean(value.needsTranslation)

    when (value.type) {
      ChatType.Chat,
      ChatType.Announcement,
      ChatType.Whisper -> {
        encodeString(value.sourceName!!)
        encodeString(value.message)
      }
      ChatType.Raw,
      ChatType.Object,
      ChatType.ObjectWhisper,
      ChatType.System,
      ChatType.Tip -> {
        encodeString(value.message)
      }
      ChatType.Translation,
      ChatType.JukeboxPopup,
      ChatType.Popup -> {
        encodeString(value.message)
        encodeArray(value.parameters) { encodeString(it) }
      }
    }

    encodeString(value.xuid)
    encodeString(value.platformChatId)
  }
}
