package com.gabrielleeg1.bedrockvoid.protocol.types

import kotlinx.serialization.Serializable

@Serializable
data class GameRule(
  val name: String,
  val canBeModifiedByPlayer: Boolean,
  val value: Value,
) {
  @Serializable
  sealed class Value

  data class BooleanValue(val boolean: Boolean) : Value()
  data class IntValue(val int: Int) : Value()
  data class FloatValue(val float: Float) : Value()
}
