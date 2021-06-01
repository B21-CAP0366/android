package com.bangkit.capstone.meater.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.meater.di.Injection
import com.bangkit.capstone.meater.data.source.AppRepository
import com.bangkit.capstone.meater.ui.result.ResultViewModel
import com.bangkit.capstone.meater.ui.home.NewsViewModel

class ViewModelFactory private constructor(private val appRepository: AppRepository, private val mApplication: Application):
    ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(), application)
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(appRepository) as T
            }

            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(mApplication) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}