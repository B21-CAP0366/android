package com.bangkit.capstone.meater.ui.home

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bangkit.capstone.meater.data.News
import com.bangkit.capstone.meater.databinding.ActivityMainBinding
import com.bangkit.capstone.meater.ui.camera.CameraActivity
import com.bangkit.capstone.meater.util.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var newsAdapter: ListNewsAdapter
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.floating.setOnClickListener {
            intent = Intent(this@MainActivity, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.rvMeater.setHasFixedSize(true)
        newsAdapter = ListNewsAdapter()

        binding.rvMeater.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rvMeater.adapter = newsAdapter

        val factory = ViewModelFactory.getInstance(application)

        newsViewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        newsViewModel.getNews().observe(this, {
            newsAdapter.rvList(it)
            newsAdapter.notifyDataSetChanged()
        })

        newsAdapter.setOnItemClickCallback(object : ListNewsAdapter.OnItemClickCallback{
            override fun onItemCallback(news: News) {
                openWebPage(news)
            }
        })
    }

    fun openWebPage(news: News) {
        val url = news.url
        val webpage: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}