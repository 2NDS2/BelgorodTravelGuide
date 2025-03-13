package com.example.belgorodtravelguide.data.local.interfaceDAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.belgorodtravelguide.data.local.entity.NewsArticleEntity

@Dao
interface NewsDao {

    // Вставить новости в бд
    @Insert
    suspend fun insert(newsArticle: NewsArticleEntity)

    @Insert
    suspend fun insertAll(newsArticles: List<NewsArticleEntity>)

    // Получить все новости
    @Query("SELECT * FROM news_table")
    suspend fun getAllNews(): List<NewsArticleEntity>

    // Удалить все новости
    @Query("DELETE FROM news_table")
    suspend fun deleteAllNews()
}
