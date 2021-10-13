package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class Vec2(
  val yaw: Float,
  val pitch: Float,
)
