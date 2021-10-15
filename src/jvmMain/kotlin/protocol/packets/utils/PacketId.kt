package com.gabrielleeg1.bedrockvoid.protocol.packets.utils

import com.gabrielleeg1.bedrockvoid.protocol.packets.Packet
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

fun getPacketId(type: KClass<*>): Int {
  val packet = type.findAnnotation<Packet>()
    ?: error("Can not get id from type $type without @Packet annotation")

  return packet.id
}
