package com.example.belgorodtravelguide.ui.viewModel.profileAndMarketVM

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.belgorodtravelguide.domain.repository.ProfileRepository

class ProfileViewModelFactory(private val repository: ProfileRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileAndMarketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileAndMarketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
