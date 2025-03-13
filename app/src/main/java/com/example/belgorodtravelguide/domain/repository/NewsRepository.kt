package com.example.belgorodtravelguide.domain.repository

import com.example.belgorodtravelguide.data.local.entity.NewsArticleEntity

interface NewsRepository {
    suspend fun getAllNews(): List<NewsArticleEntity>
    suspend fun insertNews(newsArticles: List<NewsArticleEntity>)
    suspend fun clearNews()
}