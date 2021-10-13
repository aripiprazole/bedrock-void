package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class ExperimentData(
  val name: String,
  val enabled: Boolean,
)
