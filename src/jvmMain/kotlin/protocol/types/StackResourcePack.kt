package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class StackResourcePack(
  val id: String,
  val version: String,
  val subPackName: String,
)
