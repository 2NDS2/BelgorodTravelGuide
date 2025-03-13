package com.example.belgorodtravelguide.data.repository

import com.example.belgorodtravelguide.data.local.entity.Profile
import com.example.belgorodtravelguide.data.local.db.AppDatabase
import com.example.belgorodtravelguide.domain.repository.ProfileRepository

//class ProfileRepositoryImpl(context: Context) : ProfileRepository {
class ProfileRepositoryImpl(appDatabase: AppDatabase) : ProfileRepository {

//    private val profileDao = AppDatabase.getInstance(context).profileDao
    private val profileDao = appDatabase.profileDao

    override suspend fun getProfile(): Profile? {
        return profileDao.getProfile()
    }

    override suspend fun insertProfile(profile: Profile) {
        profileDao.insert(profile)
    }

    override suspend fun updateProfile(profile: Profile) {
        profileDao.update(profile)
    }
}