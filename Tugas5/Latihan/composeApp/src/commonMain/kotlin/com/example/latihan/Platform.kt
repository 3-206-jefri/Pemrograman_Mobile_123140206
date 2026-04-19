package com.example.latihan

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform