package com.bangkit.capstone.meater.network

import com.bangkit.capstone.meater.data.NewsJsonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InterfaceApi {
    @GET(".")
    fun getNews(
        @Query("news") news: String
    ): Call<NewsJsonResponse>
}