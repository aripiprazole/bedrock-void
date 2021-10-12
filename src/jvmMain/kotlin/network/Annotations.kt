package com.gabrielleeg1.bedrockvoid.network

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Packet(val id: Int)
