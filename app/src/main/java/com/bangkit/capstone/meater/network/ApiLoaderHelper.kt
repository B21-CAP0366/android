package com.bangkit.capstone.meater.network

import com.bangkit.capstone.meater.data.NewsJsonResponse
import retrofit2.Call

class ApiLoaderHelper {
    fun findNews(news: String): Call<NewsJsonResponse> {
        return ApiClient.getApi().getNews(news)
    }
}