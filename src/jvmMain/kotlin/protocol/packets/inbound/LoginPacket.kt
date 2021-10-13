package com.gabrielleeg1.bedrockvoid.protocol.packets.inbound

import com.gabrielleeg1.bedrockvoid.protocol.Packet
import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Packet(0x01)
@Serializable
data class LoginPacket(
  val protocolVersion: Int,
  val chainData: JwtData,
  val skinData: String
) : InboundPacket {
  @Serializable
  data class JwtData(val chain: Triple<Jwt1, Jwt2, Jwt3>)

  @Serializable
  data class Jwt1(
    val nbf: Long,
    val certificateAuthority: Boolean,
    val exp: Long,
    val identityPublicKey: String,
  )

  @Serializable
  data class Jwt2(
    val nbf: Long,
    val randomNonce: Long,
    val iss: String,
    val exp: Long,
    val iat: Long,
    val identityPublicKey: String,
  )

  @Serializable
  data class Jwt3(
    val nbf: Long,
    val extraData: ExtraData,
    val randomNonce: Long,
    val iss: String,
    val exp: Long,
    val iat: Long,
    val identityPublicKey: String,
  )

  @Serializable
  data class ExtraData(
    @SerialName("XUID")
    val xuid: String,
    val identity: String,
    val displayName: String,
    val titleId: String,
  )
}
