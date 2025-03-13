package com.example.belgorodtravelguide.data.repository

import com.example.belgorodtravelguide.data.local.db.AppDatabase
import com.example.belgorodtravelguide.data.local.entity.NewsArticleEntity
import com.example.belgorodtravelguide.domain.repository.NewsRepository

class NewsRepositoryImpl(appDatabase: AppDatabase) : NewsRepository {

    private val newsDao = appDatabase.newsDao

    override suspend fun getAllNews(): List<NewsArticleEntity> {
        return newsDao.getAllNews()
    }

    override suspend fun insertNews(newsArticles: List<NewsArticleEntity>) {
        newsDao.insertAll(newsArticles)
    }

    override suspend fun clearNews() {
        newsDao.deleteAllNews()
    }
}
