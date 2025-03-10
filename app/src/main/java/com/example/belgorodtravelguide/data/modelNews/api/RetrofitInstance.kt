package com.example.belgorodtravelguide.data.modelNews.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://newsdata.io/api/"
    val retrofit:Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: NewsApiService = retrofit.create(NewsApiService::class.java)
}