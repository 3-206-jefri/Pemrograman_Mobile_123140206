package com.example.tugas1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun getPlatformName(): String