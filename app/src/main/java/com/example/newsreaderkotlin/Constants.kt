package com.example.newsreaderkotlin

class Constants {

    companion object {
        const val TAG = "DEBUG_TAG"
        const val base_url = "https://newsapi.org/"
        const val url =
            "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=6946d0c07a1c4555a4186bfcade76398"
        var TIME_OF_LAST_REFRESH_SHARED_PREFS: String = ""
        var TIME_OF_LAST_REFRESH_VALUE: String = ""
    }
}