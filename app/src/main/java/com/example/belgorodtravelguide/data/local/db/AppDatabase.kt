package com.example.belgorodtravelguide.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.belgorodtravelguide.data.local.entity.NewsArticleEntity
import com.example.belgorodtravelguide.data.local.interfaceDAO.NewsDao
import com.example.belgorodtravelguide.data.local.entity.Profile
import com.example.belgorodtravelguide.data.local.interfaceDAO.ProfileDao

@Database(entities = [Profile::class, NewsArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val profileDao: ProfileDao
    abstract val newsDao: NewsDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance // экземпляр БД
            }
        }
    }
}