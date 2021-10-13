@file:Suppress("UNCHECKED_CAST")

package com.gabrielleeg1.bedrockvoid

import com.gabrielleeg1.bedrockvoid.protocol.PacketDeserializer
import com.gabrielleeg1.bedrockvoid.protocol.PacketDeserializerMap
import com.gabrielleeg1.bedrockvoid.protocol.PacketSerializer
import com.gabrielleeg1.bedrockvoid.protocol.PacketSerializerMap
import com.gabrielleeg1.bedrockvoid.protocol.VarInt
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ClientCacheStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.InboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.LoginPacket.JwtData
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ResourcePackResponsePacket.Status
import com.gabrielleeg1.bedrockvoid.protocol.packets.inbound.ViolationWarningPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.DisconnectPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.OutboundHandshakePacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.PlayStatusPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePackStackPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePackStackPacket.ResourcePack
import com.gabrielleeg1.bedrockvoid.protocol.packets.outbound.ResourcePacksInfoPacket
import com.gabrielleeg1.bedrockvoid.protocol.readVarInt
import com.gabrielleeg1.bedrockvoid.protocol.writeVarInt
import io.netty.buffer.ByteBuf
import kotlinx.serialization.decodeFromString
import java.util.Base64

private object DisconnectPacketSerializer : PacketSerializer<DisconnectPacket> {
  override fun ByteBuf.serialize(value: DisconnectPacket) {
    writeBoolean(value.hideDisconnectPacket)
    writeString(value.kickMessage)
  }
}

private object LoginPacketDeserializer : PacketDeserializer<LoginPacket> {
  private fun decodeBase64(base64: String): String {
    return Base64.getDecoder().decode(base64.split("\\.".toRegex())[1]).decodeToString()
  }

  override fun ByteBuf.deserialize(): LoginPacket {
    val protocolVersion = readInt()

    val jwt = readSlice(readVarInt().value)

    val chainData = json.decodeFromString<Map<String, List<String>>>(jwt.readLEString()).run {
      val (fst, snd, third) = get("chain") ?: error("'chain' does not exist in 'chainData'")

      JwtData(
        chain = Triple(
          json.decodeFromString(decodeBase64(fst)),
          json.decodeFromString(decodeBase64(snd)),
          json.decodeFromString(decodeBase64(third)),
        ),
      )
    }
    val skinData = jwt.readLEString()

    return LoginPacket(protocolVersion, chainData, skinData)
  }
}

private object ClientCacheStatusPacketDeserializer : PacketDeserializer<ClientCacheStatusPacket> {
  override fun ByteBuf.deserialize(): ClientCacheStatusPacket {
    return ClientCacheStatusPacket(readBoolean())
  }
}

private object ViolationWarningPacketDeserializer : PacketDeserializer<ViolationWarningPacket> {
  override fun ByteBuf.deserialize(): ViolationWarningPacket {
    return ViolationWarningPacket()
  }
}

private object InboundHandshakePacketDeserializer : PacketDeserializer<InboundHandshakePacket> {
  override fun ByteBuf.deserialize(): InboundHandshakePacket {
    return InboundHandshakePacket()
  }
}

private object OutboundHandshakePacketSerializer : PacketSerializer<OutboundHandshakePacket> {
  override fun ByteBuf.serialize(value: OutboundHandshakePacket) {
    writeVarInt(VarInt(value.jwtData.length))
    writeBytes(value.jwtData.toByteArray())
  }
}

private object PlayStatusPacketSerializer : PacketSerializer<PlayStatusPacket> {
  override fun ByteBuf.serialize(value: PlayStatusPacket) {
    writeInt(value.status.ordinal)
  }
}

private object ResourcePacksInfoPacketSerializer : PacketSerializer<ResourcePacksInfoPacket> {
  override fun ByteBuf.serialize(value: ResourcePacksInfoPacket) {
    writeBoolean(value.requireAccept)
    writeBoolean(value.scriptingEnabled)
    writeBoolean(value.forcingServerPacksEnabled)
    writeArrayShortLE(value.behaviorPackInfos) { info ->
      writeString(info.id)
      writeString(info.version)
      writeLongLE(info.size)
      writeString(info.contentKey)
      writeString(info.subPackName)
      writeString(info.contentId)
      writeBoolean(info.scriptingEnabled)
    }
    writeArrayShortLE(value.resourcePackInfos) { info ->
      writeString(info.id)
      writeString(info.version)
      writeLongLE(info.size)
      writeString(info.contentKey)
      writeString(info.subPackName)
      writeString(info.contentId)
      writeBoolean(info.scriptingEnabled)
      writeBoolean(info.raytracingCapable)
    }
  }
}

private object ResourcePackResponsePacketDeserializer :
  PacketDeserializer<ResourcePackResponsePacket> {
  override fun ByteBuf.deserialize(): ResourcePackResponsePacket {
    val status = Status.values()[readUnsignedByte().toInt()]
    val ids = readArrayShortLE { readString() }

    return ResourcePackResponsePacket(status, ids)
  }
}

private object ResourcePackStackPacketSerializer : PacketSerializer<ResourcePackStackPacket> {
  private fun ByteBuf.writeResourcePack(pack: ResourcePack) {
    writeString(pack.id)
    writeString(pack.version)
    writeString(pack.subPackName)
  }

  override fun ByteBuf.serialize(value: ResourcePackStackPacket) {
    writeBoolean(value.requireAccept)
    writeArray(value.behaviorPacks) { writeResourcePack(it) }
    writeArray(value.resourcePacks) { writeResourcePack(it) }
    writeString(value.gameVersion)
    writeArrayIntLE(value.experiments) { experiment ->
      writeString(experiment.name)
      writeBoolean(experiment.enabled)
    }
    writeBoolean(value.experimentsPreviouslyToggled)
  }
}

val serializers = mapOf(
  0x03 to OutboundHandshakePacketSerializer,
  0x02 to PlayStatusPacketSerializer,
  0x05 to DisconnectPacketSerializer,
  0x06 to ResourcePacksInfoPacketSerializer,
  0x07 to ResourcePackStackPacketSerializer,
) as PacketSerializerMap

val deserializers = mapOf(
  0x01 to LoginPacketDeserializer,
  0x04 to InboundHandshakePacketDeserializer,
  0x08 to ResourcePackResponsePacketDeserializer,
  0x81 to ClientCacheStatusPacketDeserializer,
  0x9C to ViolationWarningPacketDeserializer,
) as PacketDeserializerMap
