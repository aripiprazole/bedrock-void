package com.gabrielleeg1.bedrockvoid.protocol.serialization.types

import com.gabrielleeg1.bedrockvoid.protocol.serialization.DecodingStream
import com.gabrielleeg1.bedrockvoid.protocol.serialization.Encoder
import com.gabrielleeg1.bedrockvoid.protocol.serialization.EncodingStream
import com.gabrielleeg1.bedrockvoid.protocol.types.BlockPos

object BlockPosEncoder : Encoder<BlockPos> {
  override fun EncodingStream.encodeT(value: BlockPos) {
    encodeVarInt(value.x)
    encodeVarUInt(value.y.toUInt())
    encodeVarInt(value.z)
  }

  override fun DecodingStream.decodeT(): BlockPos {
    return BlockPos(decodeVarInt(), decodeVarUInt().toInt(), decodeVarInt())
  }
}
