package com.example.newsreaderkotlin.Models

import com.google.gson.annotations.SerializedName

data class NewsListItem(
    @SerializedName("articles")
    val newsItemList: List<NewsItem>
)
