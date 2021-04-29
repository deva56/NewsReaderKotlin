package com.example.newsreaderkotlin.Repository

import com.example.newsreaderkotlin.Models.NewsListItem
import com.example.newsreaderkotlin.Network.RetrofitInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val retrofitInterface: RetrofitInterface) {

    suspend fun getNewsList(url: String): Response<NewsListItem> {
        return withContext(Dispatchers.IO) {
            retrofitInterface.getNewsList(url)
        }
    }
}