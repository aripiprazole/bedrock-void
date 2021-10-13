package com.gabrielleeg1.bedrockvoid.logging

import ch.qos.logback.classic.Level

enum class LogLevel(private val level: String, private val color: String) {
  Info("INFO", LogColor.LightYellow),
  Warn("WARN", LogColor.Yellow),
  Error("ERROR", LogColor.Red),
  Debug("DEBUG", LogColor.Cyan),
  Trace("TRACE", LogColor.Blue),
  All("ALL", Trace.color),
  None("", "");

  override fun toString() = color + level

  companion object {
    fun fromLogbackLevel(level: Level) = values()
      .find { it.level.equals(level.levelStr, ignoreCase = true) } ?: None
  }
}
