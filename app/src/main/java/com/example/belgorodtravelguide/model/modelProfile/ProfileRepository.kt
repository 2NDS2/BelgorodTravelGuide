package com.example.belgorodtravelguide.model.modelProfile

import android.content.Context

class ProfileRepository {
        suspend fun getProfile(context: Context): Profile? {
            val profileDao = ProfileDatabase.getInstance(context).profileDao
            return profileDao.getProfile()
        }

        suspend fun insertProfile(profile: Profile, context: Context) {
            val profileDao = ProfileDatabase.getInstance(context).profileDao
            profileDao.insert(profile)
        }

        suspend fun updateProfile(profile: Profile, context: Context) {
            val profileDao = ProfileDatabase.getInstance(context).profileDao
            profileDao.update(profile)
        }
}