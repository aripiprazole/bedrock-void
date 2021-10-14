package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import kotlinx.serialization.json.Json
import protocol.serialization.DecodingStrategy
import protocol.serialization.EncodingStrategy

class PacketCodec(
  val json: Json,
  val inboundPackets: Map<Int, DecodingStrategy<out InboundPacket>>,
  val outboundPackets: Map<Int, EncodingStrategy<out OutboundPacket>>,
)
