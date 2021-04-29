package com.example.newsreaderkotlin.ViewModel

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsreaderkotlin.Constants
import com.example.newsreaderkotlin.Constants.Companion.TIME_OF_LAST_REFRESH_SHARED_PREFS
import com.example.newsreaderkotlin.Constants.Companion.TIME_OF_LAST_REFRESH_VALUE
import com.example.newsreaderkotlin.Entity.NewsEntity
import com.example.newsreaderkotlin.Models.NewsItem
import com.example.newsreaderkotlin.Repository.DatabaseRepository
import com.example.newsreaderkotlin.Repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepository,
    private val networkRepository: NetworkRepository,
    application: Application,
) : AndroidViewModel(application) {

    private val newsEntityList: MutableList<NewsEntity> = ArrayList()
    private val liveDataNewsEntity: MutableLiveData<List<NewsEntity>> = MutableLiveData()
    private val throwableLiveData: MutableLiveData<String> = MutableLiveData()

    fun getNewsList(url: String) {
        viewModelScope.launch {
            try {
                val result = networkRepository.getNewsList(url)
                if (result.isSuccessful) {

                    val newsItemList: List<NewsItem> = result.body()!!.newsItemList

                    for (newsEntity in newsItemList) {
                        val entity = NewsEntity(
                            newsEntity.author,
                            newsEntity.title,
                            newsEntity.description,
                            newsEntity.url,
                            newsEntity.urlToImage,
                            newsEntity.publishedAt,
                            0
                        )
                        newsEntityList.add(entity)
                        insert(entity)
                    }

                    val sharedPreferencesBackgroundTerminated: SharedPreferences =
                        getApplication<Application>().applicationContext.getSharedPreferences(
                            TIME_OF_LAST_REFRESH_SHARED_PREFS, MODE_PRIVATE
                        )
                    val editor = sharedPreferencesBackgroundTerminated.edit()
                    editor.putString(
                        TIME_OF_LAST_REFRESH_VALUE,
                        Calendar.getInstance().timeInMillis.toString()
                    )
                    editor.apply()

                    liveDataNewsEntity.postValue(newsEntityList)

                } else {
                    throwableLiveData.postValue(result.errorBody().toString())
                }
            } catch (e: Exception) {
                throwableLiveData.postValue(e.message)
            }
        }
    }

    private fun insert(newsEntity: NewsEntity) {
        viewModelScope.launch {
            try {
                databaseRepository.insert(newsEntity)
            } catch (e: Exception) {
                throwableLiveData.postValue(e.message)
            }
        }
    }

    fun deleteAllRecords() {
        viewModelScope.launch {
            try {
                databaseRepository.deleteAllRecords()
                getNewsList(Constants.url)
            } catch (e: Exception) {
                throwableLiveData.postValue(e.message)
            }
        }
    }

    fun getAllRecords() {
        viewModelScope.launch {
            try {
                val result = databaseRepository.getAllRecords()
                liveDataNewsEntity.postValue(result)
            } catch (e: Exception) {
                throwableLiveData.postValue(e.message)
            }
        }
    }

    fun getLiveDataNewsEntity(): LiveData<List<NewsEntity>> {
        return liveDataNewsEntity
    }

    fun getThrowableLiveData(): LiveData<String> {
        return throwableLiveData
    }

}