package com.example.belgorodtravelguide.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.belgorodtravelguide.data.modelNews.entityAndDao.NewsArticleEntity
import com.example.belgorodtravelguide.data.modelNews.entityAndDao.NewsDao
import com.example.belgorodtravelguide.data.modelProfile.Profile
import com.example.belgorodtravelguide.data.modelProfile.ProfileDao

@Database(entities = [Profile::class, NewsArticleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val profileDao: ProfileDao
    abstract fun newsDao(): NewsDao

    //abstract val newsDao: NewsDao

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