package com.bangkit.capstone.meater.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.capstone.meater.di.Injection
import com.bangkit.capstone.meater.data.source.AppRepository

class ViewModelFactory private constructor(private val appRepository: AppRepository):
    ViewModelProvider.NewInstanceFactory(){

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository())
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when{

            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(appRepository) as T
            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}