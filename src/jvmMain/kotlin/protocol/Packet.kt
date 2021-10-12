package com.gabrielleeg1.bedrockvoid.protocol

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Packet(val id: Int)
