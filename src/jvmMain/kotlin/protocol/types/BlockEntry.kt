package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class BlockEntry(
  val name: String,
  val properties: Map<String, NbtValue>
)
