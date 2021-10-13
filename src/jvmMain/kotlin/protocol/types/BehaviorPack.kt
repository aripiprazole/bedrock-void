package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class BehaviorPack(
  val id: String,
  val version: String,
  val size: Long,
  val contentKey: String,
  val subPackName: String,
  val contentId: String,
  val scriptingEnabled: Boolean,
)
