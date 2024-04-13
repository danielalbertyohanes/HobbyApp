package com.example.hobbyapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyapp.databinding.NewsListItemBinding
import com.example.hobbyapp.model.News


class NewsListAdapter(val newsList:ArrayList<News>): RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>()  {

    class NewsViewHolder(var binding: NewsListItemBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding= NewsListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}