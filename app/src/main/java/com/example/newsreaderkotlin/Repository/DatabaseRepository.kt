package com.example.newsreaderkotlin.Repository

import com.example.newsreaderkotlin.DAO.NewsDAO
import com.example.newsreaderkotlin.Database.NewsDatabase
import com.example.newsreaderkotlin.Entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor(
    private val newsDatabase: NewsDatabase,
    private val newsDAO: NewsDAO = newsDatabase.newsDao()
) {

    suspend fun insert(newsEntity: NewsEntity) {
        return withContext(Dispatchers.IO) {
            newsDAO.insert(newsEntity)
        }
    }

    suspend fun deleteAllRecords() {
        return withContext(Dispatchers.IO) {
            newsDAO.deleteAllRecord()
        }
    }

    suspend fun getAllRecords(): List<NewsEntity> {
        return withContext(Dispatchers.IO) {
            newsDAO.getAllRecord()
        }
    }
}