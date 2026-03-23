package com.example.tugas4

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform