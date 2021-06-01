package com.bangkit.capstone.meater.data.source

import androidx.lifecycle.LiveData
import com.bangkit.capstone.meater.data.News

interface AppDataSource {
    fun getNews(news: String): LiveData<List<News>>
}