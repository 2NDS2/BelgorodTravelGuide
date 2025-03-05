package com.example.belgorodtravelguide.model.modelNews.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("1/news")
    fun getNews(
        @Query("apikey") apiKey: String,// = "pub_7161479900d23567d2b4226f29a68b679f112",
        @Query("q") query: String,// = "Белгород",
        @Query("country") сountry: String,// = "ru",
        @Query("language") language: String,// = "ru"
        @Query("page") page: String? = null// = "ru"
    ): Call<NewsResponse>
}