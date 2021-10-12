package com.gabrielleeg1.bedrockvoid.network

import com.gabrielleeg1.bedrockvoid.network.packets.InboundPacket
import io.netty.buffer.ByteBuf

typealias PacketDeserializerMap = Map<Int, PacketDeserializer<InboundPacket>>

fun interface PacketDeserializer<T> {
    fun ByteBuf.deserialize(): T
}
