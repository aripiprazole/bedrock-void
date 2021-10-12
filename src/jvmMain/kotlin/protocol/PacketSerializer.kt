package com.gabrielleeg1.bedrockvoid.protocol

import com.gabrielleeg1.bedrockvoid.protocol.packets.OutboundPacket
import io.netty.buffer.ByteBuf

typealias PacketSerializerMap = Map<Int, PacketSerializer<OutboundPacket>>

fun interface PacketSerializer<T> {
  fun ByteBuf.serialize(value: T)
}
