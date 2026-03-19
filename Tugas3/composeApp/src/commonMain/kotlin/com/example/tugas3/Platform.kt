package com.example.tugas3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform