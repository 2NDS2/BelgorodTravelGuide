package com.example.belgorodtravelguide.data.repository

import android.content.Context
import com.example.belgorodtravelguide.data.modelProfile.Profile
import com.example.belgorodtravelguide.data.db.AppDatabase

class ProfileRepository {
        suspend fun getProfile(context: Context): Profile? {
            val profileDao = AppDatabase.getInstance(context).profileDao
            return profileDao.getProfile()
        }

        suspend fun insertProfile(profile: Profile, context: Context) {
            val profileDao = AppDatabase.getInstance(context).profileDao
            profileDao.insert(profile)
        }

        suspend fun updateProfile(profile: Profile, context: Context) {
            val profileDao = AppDatabase.getInstance(context).profileDao
            profileDao.update(profile)
        }
}