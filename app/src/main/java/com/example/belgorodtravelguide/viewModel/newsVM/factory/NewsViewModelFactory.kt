package com.example.belgorodtravelguide.viewModel.newsVM.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.belgorodtravelguide.data.modelNews.bd.NewsDatabase
import com.example.belgorodtravelguide.viewModel.newsVM.NewsViewModel

class NewsViewModelFactory(
    private val newsDatabase: NewsDatabase,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsDatabase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
