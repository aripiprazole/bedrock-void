package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class Vec3(
  val x: Float,
  val y: Float,
  val z: Float,
)
