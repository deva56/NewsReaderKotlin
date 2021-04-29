package com.example.newsreaderkotlin.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsreaderkotlin.Entity.NewsEntity

@Dao
interface NewsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(newsEntity: NewsEntity)

    @Query("DELETE FROM news_table")
    suspend fun deleteAllRecord()

    @Query("SELECT * FROM news_table")
    suspend fun getAllRecord(): List<NewsEntity>
}