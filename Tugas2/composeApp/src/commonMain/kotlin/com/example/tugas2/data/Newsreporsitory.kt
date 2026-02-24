package com.example.tugas2.data

import com.example.tugas2.model.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository {

    private val categories = listOf("Tech", "Sport", "Health")

    fun getNewsStream(): Flow<News> = flow {
        var id = 1
        while (true) {
            delay(2000) // setiap 2 detik
            emit(
                News(
                    id = id,
                    title = "Berita $id",
                    category = categories.random(),
                    content = "Ini adalah detail berita nomor $id"
                )
            )
            id++
        }
    }

    suspend fun getNewsDetail(news: News): String {
        delay(1000) // simulasi async
        return news.content
    }
}