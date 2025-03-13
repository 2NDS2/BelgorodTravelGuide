package com.example.belgorodtravelguide.ui.viewModel.newsVM.factory


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.belgorodtravelguide.domain.repository.NewsRepository
import com.example.belgorodtravelguide.ui.viewModel.newsVM.NewsViewModel

class NewsViewModelFactory(
    private val newsRepository: NewsRepository, // Используем интерфейс вместо реализации
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(newsRepository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
