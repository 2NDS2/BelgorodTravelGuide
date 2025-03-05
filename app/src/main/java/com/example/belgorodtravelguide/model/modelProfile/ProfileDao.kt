package com.example.belgorodtravelguide.model.modelProfile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfileDao {

    @Insert
    suspend fun insert(profile: Profile)
    @Update
    suspend fun update(profile: Profile)
    @Delete
    suspend fun delete(profile: Profile)

    @Query("SELECT * FROM profile_table")
    suspend fun getProfile(): Profile?

    @Query("DELETE FROM profile_table")
    suspend fun deleteAllProfiles()




}

