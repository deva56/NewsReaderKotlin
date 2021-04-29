package com.example.newsreaderkotlin.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newsreaderkotlin.DAO.NewsDAO
import com.example.newsreaderkotlin.Entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDAO
}