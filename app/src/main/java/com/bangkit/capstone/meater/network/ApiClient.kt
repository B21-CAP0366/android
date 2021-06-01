package com.bangkit.capstone.meater.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object {

        private const val BASE_URL = "https://backend-meater-3jtx22rw2a-as.a.run.app/"

        fun getApi(): InterfaceApi {

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofitBuilder.create(InterfaceApi::class.java)
        }

    }

}
