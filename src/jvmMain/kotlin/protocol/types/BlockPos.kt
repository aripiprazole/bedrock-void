package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class BlockPos(val x: Int, val y: Int, val z: Int)
