package com.example.tugas6.data.repository

import com.example.tugas6.data.model.Article
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.delay

class NewsRepository(private val client: HttpClient) {
    private val baseUrl = "asd"

    suspend fun getArticles(): Result<List<Article>> {
        return try {
            delay(2000)
            val articles: List<Article> = client.get("$baseUrl/posts").body()
            Result.success(articles)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            val article: Article = client.get("$baseUrl/posts/$id").body()
            Result.success(article)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}