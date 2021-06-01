package com.bangkit.capstone.meater.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.capstone.meater.data.News
import com.bangkit.capstone.meater.data.source.AppRepository

class NewsViewModel(private var newsAppRepository: AppRepository): ViewModel() {

    companion object {
        const val NEWS = "news"
    }

    fun getNews(): LiveData<List<News>> = newsAppRepository.getNews(NEWS)

}