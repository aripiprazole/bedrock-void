package com.gabrielleeg1.bedrockvoid.protocol.serialization.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.JwtData
import kotlinx.serialization.decodeFromString
import protocol.serialization.DecodingStrategy
import java.util.Base64

object LoginPacketEncoder : DecodingStrategy<LoginPacket> {
  private fun decodeBase64(base64: String): String {
    return Base64.getDecoder().decode(base64.split("\\.".toRegex())[1]).decodeToString()
  }

  override fun DecodingStream.decodeT(): LoginPacket {
    val protocolVersion = decodeInt()

    val jwt = decodeSlice(decodeVarInt())

    val chainData = json.decodeFromString<Map<String, List<String>>>(jwt.decodeStringLE()).run {
      val (fst, snd, third) = get("chain") ?: error("'chain' does not exist in 'chainData'")

      JwtData(
        chain = Triple(
          json.decodeFromString(decodeBase64(fst)),
          json.decodeFromString(decodeBase64(snd)),
          json.decodeFromString(decodeBase64(third)),
        ),
      )
    }
    val skinData = jwt.decodeStringLE()

    return LoginPacket(
      protocolVersion,
      chainData,
      skinData,
    )
  }
}
