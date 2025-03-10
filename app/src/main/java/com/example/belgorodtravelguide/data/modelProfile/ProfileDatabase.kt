package com.example.belgorodtravelguide.data.modelProfile

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.belgorodtravelguide.data.modelNews.bd.NewsArticleEntity
import com.example.belgorodtravelguide.data.modelNews.bd.NewsDao

@Database(entities = [Profile::class, NewsArticleEntity::class], version = 1, exportSchema = false)
abstract class ProfileDatabase: RoomDatabase() {
    abstract val profileDao: ProfileDao
    abstract val newsDao: NewsDao

    companion object{
        @Volatile
        private var INSTANCE: ProfileDatabase? = null

        fun getInstance(context: Context): ProfileDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProfileDatabase::class.java,
                        "profile_database"
                    )
                        .build()
                    INSTANCE = instance
                }
                return instance // экземпляр БД
            }
        }
    }
}