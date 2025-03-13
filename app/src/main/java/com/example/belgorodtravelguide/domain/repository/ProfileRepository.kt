package com.example.belgorodtravelguide.domain.repository

import com.example.belgorodtravelguide.data.local.entity.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile?
    suspend fun insertProfile(profile: Profile)
    suspend fun updateProfile(profile: Profile)
}