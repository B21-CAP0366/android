package com.bangkit.capstone.meater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.capstone.meater.data.News
import com.bangkit.capstone.meater.databinding.NewsCardItemBinding
import com.bumptech.glide.Glide

class ListNewsAdapter(): RecyclerView.Adapter<ListNewsAdapter.ListViewHolder>() {

    private var newsItem = ArrayList<News>()
    private var itemClickCallback: OnItemClickCallback? = null

    inner class ListViewHolder(private val binding: NewsCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: News) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(news.urlToImage)
                    .centerCrop()
                    .into(imgCvPhoto)
                tvCvTitle.text = news.title
                tvDesc.text = news.description

                binding.root.setOnClickListener {
                    itemClickCallback?.onItemCallback(news)

                    itemView.setOnClickListener { itemClickCallback?.onItemCallback(news) }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val newsBinding = NewsCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(newsBinding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val news = newsItem[position]
        holder.bind(news)
    }

    override fun getItemCount(): Int = newsItem.size

    fun rvList(news: List<News>){
        newsItem.clear()
        newsItem.addAll(news)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.itemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemCallback(news: News)
    }
}