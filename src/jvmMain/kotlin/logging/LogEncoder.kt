package com.gabrielleeg1.bedrockvoid.logging

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.encoder.EncoderBase
import com.gabrielleeg1.bedrockvoid.logging.LogColor.Cyan
import com.gabrielleeg1.bedrockvoid.logging.LogColor.Red
import com.gabrielleeg1.bedrockvoid.logging.LogColor.Reset
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LogEncoder(private val dateFormatter: DateTimeFormatter) : EncoderBase<ILoggingEvent>() {
  override fun headerBytes() = byteArrayOf()
  override fun footerBytes() = byteArrayOf()

  override fun encode(event: ILoggingEvent): ByteArray {
    val time = dateFormatter.format(LocalDateTime.now())
    val level = LogLevel.fromLogbackLevel(event.level)

    val name = event.loggerName.split(".").let { it[it.size - 1] }
    val thread = event.threadName + runBlocking {
      val coroutineName = currentCoroutineContext()[CoroutineName] ?: return@runBlocking ""

      "(${coroutineName.name})"
    }

    val message = if (event.throwableProxy == null) {
      event.message + Reset
    } else {
      event.throwableProxy!!.stackTraceElementProxyArray?.let {
        val cause = event.throwableProxy

        val causeMessage = "${cause?.className}: ${cause?.message}"

        listOf(LINE_SEPARATOR + Red + causeMessage, *it)
          .joinToString(separator = "") { element ->
            "\t$Red$element$LINE_SEPARATOR"
          }
      }
    }

    return FORMAT.format(time, level, name, thread, message).toByteArray()
  }

  companion object {
    private const val LINE_SEPARATOR = "\r\n"
    private const val FORMAT = "[%s] %s$Reset in$Cyan %s$Reset at $Cyan%s$Reset: %s$Reset$LINE_SEPARATOR"
  }
}
