package com.bangkit.capstone.meater.di

import com.bangkit.capstone.meater.data.Remote.RemoteDataSource
import com.bangkit.capstone.meater.data.source.AppRepository
import com.bangkit.capstone.meater.network.ApiLoaderHelper

object Injection {
    fun provideRepository(): AppRepository {
        val remoteDataSource = RemoteDataSource.getInstance(ApiLoaderHelper())

        return AppRepository.getInstance(remoteDataSource)
    }
}