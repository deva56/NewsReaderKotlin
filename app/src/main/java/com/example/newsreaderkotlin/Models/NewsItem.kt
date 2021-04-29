package com.example.newsreaderkotlin.Models

data class NewsItem(
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
)
