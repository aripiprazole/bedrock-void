package com.gabrielleeg1.bedrockvoid.protocol.packets.utils

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import protocol.serialization.DecodingStrategy
import protocol.serialization.EncodingStrategy
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.jvm.jvmErasure
import kotlin.reflect.typeOf

fun getPacketId(type: KType): Int {
  val packet = type.jvmErasure.findAnnotation<Packet>()
    ?: error("Can not get id from type $type without @Packet annotation")

  return packet.id
}

@ExperimentalStdlibApi
inline fun <reified T : OutboundPacket> withPacketId(value: EncodingStrategy<T>): Pair<Int, EncodingStrategy<T>> {
  return getPacketId(typeOf<T>()) to value
}

@ExperimentalStdlibApi
inline fun <reified T : InboundPacket> withPacketId(value: DecodingStrategy<T>): Pair<Int, DecodingStrategy<T>> {
  return getPacketId(typeOf<T>()) to value
}
