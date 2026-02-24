package com.example.tugas2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform