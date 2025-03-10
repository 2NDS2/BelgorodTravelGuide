package com.example.belgorodtravelguide.data.modelNews.entityAndDao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,//?,
    val imageUrl: String?,
    val url: String
)
