package com.gabrielleeg1.bedrockvoid.protocol

import com.gabrielleeg1.bedrockvoid.protocol.packets.InboundPacket
import io.netty.buffer.ByteBuf

typealias PacketDeserializerMap = Map<Int, PacketDeserializer<InboundPacket>>

fun interface PacketDeserializer<T> {
  fun ByteBuf.deserialize(): T
}
