package com.bangkit.capstone.meater.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.capstone.meater.data.News
import com.bangkit.capstone.meater.data.NewsJsonResponse
import com.bangkit.capstone.meater.data.Remote.RemoteDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository (private val remoteDataSource: RemoteDataSource) :
    AppDataSource {

    companion object {
        @Volatile
        private var instance: AppRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource)
        : AppRepository = instance?: synchronized(this) {
            instance?: AppRepository(remoteDataSource)
        }

        private const val TAG = "AppRepository"
    }

    override fun getNews(news: String): LiveData<List<News>> {
        val newsResults = MutableLiveData<List<News>>()

        remoteDataSource.getNews(news, object : RemoteDataSource.LoadNewsCallback {

            override fun onAllNewsReceived(news: Call<NewsJsonResponse>) {
                news.enqueue(object : Callback<NewsJsonResponse> {
                    override fun onResponse(
                        call: Call<NewsJsonResponse>,
                        response: Response<NewsJsonResponse>
                    ) {
                        if (response.isSuccessful){
                            newsResults.postValue(response.body()?.news)
                            Log.d("Success","${response.body()?.news}")
                        } else {
                            Log.d("Success","Failure")
                        }
                    }
                    override fun onFailure(call: Call<NewsJsonResponse>, t: Throwable) {
                        Log.d("OnFailure", t.message.toString())
                    }

                })
            }
        })
        return newsResults
    }



}