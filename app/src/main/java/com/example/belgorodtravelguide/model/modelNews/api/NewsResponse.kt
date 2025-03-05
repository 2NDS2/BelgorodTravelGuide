package com.example.belgorodtravelguide.model.modelNews.api

data class NewsResponse(
    val status: String,
    val totalResult: Int,
    val results: List<NewsArticle>,
    val nextPage: String?
)

data class NewsArticle (
    val article_id:String,
    val title:String,
    val link: String,
    val description: String?,
    val pubDate: String,
    val image_url:String?,
    val source_name:String
)

