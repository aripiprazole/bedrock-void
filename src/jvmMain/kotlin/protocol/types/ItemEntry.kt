package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class ItemEntry(
  val name: String,
  val runtimeId: Short,
  val componentBased: Boolean,
)
