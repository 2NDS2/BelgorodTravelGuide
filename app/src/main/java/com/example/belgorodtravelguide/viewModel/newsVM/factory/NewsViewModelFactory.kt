package com.example.belgorodtravelguide.viewModel.newsVM.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.belgorodtravelguide.data.db.AppDatabase
import com.example.belgorodtravelguide.viewModel.newsVM.NewsViewModel

class NewsViewModelFactory(
    private val appDatabase: AppDatabase,
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(appDatabase, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
