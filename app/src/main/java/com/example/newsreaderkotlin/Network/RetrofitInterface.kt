package com.example.newsreaderkotlin.Network

import com.example.newsreaderkotlin.Models.NewsListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitInterface {
    @GET
    suspend fun getNewsList(@Url url: String): Response<NewsListItem>
}