package com.bangkit.capstone.meater.data.Remote

import com.bangkit.capstone.meater.data.NewsJsonResponse
import com.bangkit.capstone.meater.network.ApiLoaderHelper
import retrofit2.Call

class RemoteDataSource private constructor (private val apiLoaderHelper: ApiLoaderHelper) {

    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiLoaderHelper: ApiLoaderHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiLoaderHelper)
            }
    }

    fun getNews(news: String, callback: LoadNewsCallback){
        callback.onAllNewsReceived(apiLoaderHelper.findNews(news))
    }

    interface LoadNewsCallback {
        fun onAllNewsReceived(news: Call<NewsJsonResponse>)
    }
}