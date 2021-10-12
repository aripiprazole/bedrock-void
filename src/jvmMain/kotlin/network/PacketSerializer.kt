package com.gabrielleeg1.bedrockvoid.network

import com.gabrielleeg1.bedrockvoid.network.packets.OutboundPacket
import io.netty.buffer.ByteBuf

typealias PacketSerializerMap = Map<Int, PacketSerializer<OutboundPacket>>

fun interface PacketSerializer<T> {
    fun ByteBuf.serialize(value: T)
}
