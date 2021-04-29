package com.example.newsreaderkotlin.Database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseBuilder {

    @Provides
    @Singleton
    fun getDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context.applicationContext, NewsDatabase::class.java,
            "news_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun getDAO(db: NewsDatabase) = db.newsDao()
}