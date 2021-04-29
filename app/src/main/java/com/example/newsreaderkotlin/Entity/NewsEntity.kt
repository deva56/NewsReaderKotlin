package com.example.newsreaderkotlin.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_table")
data class NewsEntity(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    @PrimaryKey(autoGenerate = true)
    val id: Int
)
