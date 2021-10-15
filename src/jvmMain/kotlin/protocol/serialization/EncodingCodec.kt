package com.gabrielleeg1.bedrockvoid.protocol.serialization

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.utils.getPacketId
import io.netty.buffer.ByteBuf
import kotlinx.serialization.json.Json
import protocol.serialization.DecodingStrategy
import protocol.serialization.EncodingStrategy
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.starProjectedType

@Suppress("UNCHECKED_CAST")
class EncodingCodec(val json: Json, strategies: Map<KClass<*>, Any>) {
  val encoders = strategies.filter { (_, strategy) -> strategy is EncodingStrategy<*> } as
    Map<KClass<Any>, EncodingStrategy<Any>>

  val decoders = strategies.filter { (_, strategy) -> strategy is DecodingStrategy<*> } as
    Map<KClass<Any>, DecodingStrategy<Any>>

  val inboundPackets = decoders
    .filter { (kClass) -> kClass.isSubclassOf(InboundPacket::class) }
    .mapKeys { (kClass) -> getPacketId(kClass.starProjectedType) } as
    Map<Int, DecodingStrategy<InboundPacket>>

  val outboundPackets = encoders
    .filter { (kClass) -> kClass.isSubclassOf(OutboundPacket::class) }
    .mapKeys { (kClass) -> getPacketId(kClass.starProjectedType) } as
    Map<Int, EncodingStrategy<OutboundPacket>>

  fun encodingStream(buf: ByteBuf): EncodingStream {
    return ByteBufEncodingStream(buf, this, json)
  }

  fun decodingStream(buf: ByteBuf): DecodingStream {
    return ByteBufDecodingStream(buf, this, json)
  }
}
