package com.bangkit.capstone.meater.network

import com.bangkit.capstone.meater.data.JsonResponse
import retrofit2.Call
import retrofit2.http.GET

interface InterfaceApi {
    @GET("posts")
    fun getDataPost(): Call<JsonResponse>
}