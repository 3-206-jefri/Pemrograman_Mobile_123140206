package com.example.tugas9.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform