package com.gabrielleeg1.bedrockvoid.network.utils

import com.nukkitx.natives.util.Natives
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.util.zip.Deflater

const val BUFFER_SIZE = 12 * 1024 * 1024

fun ByteBuf.compress(): ByteBuf {
    val deflater = Natives.ZLIB.get().create(7, true).apply {
        setInput(nioBuffer())
        setLevel(Deflater.DEFAULT_COMPRESSION)
    }

    return ByteArrayOutputStream(capacity()).use { outputStream ->
        val buffer = ByteBuffer.allocate(BUFFER_SIZE)

        while (!deflater.finished()) {
            outputStream.write(buffer.array(), 0, deflater.deflate(buffer))
        }

        Unpooled.wrappedBuffer(outputStream.toByteArray())
    }
}

fun ByteBuf.decompress(): ByteBuf {
    val inflater = Natives.ZLIB.get().create(true).apply {
        setInput(nioBuffer())
    }

    return ByteArrayOutputStream(capacity()).use { outputStream ->
        val buffer = ByteBuffer.allocate(BUFFER_SIZE)

        while (!inflater.finished()) {
            outputStream.write(buffer.array(), 0, inflater.inflate(buffer))
        }

        Unpooled.wrappedBuffer(outputStream.toByteArray())
    }
}
