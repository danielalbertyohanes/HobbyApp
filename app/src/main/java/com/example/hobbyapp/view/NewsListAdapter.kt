package com.example.hobbyapp.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.NewsListItemBinding
import com.example.hobbyapp.model.News
import com.example.hobbyapp.util.loadImage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


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
        holder.binding.txtTitle.text = newsList[position].title
        holder.binding.txtAuthor.text = "@"+newsList[position].author
        holder.binding.txtDeskripsiSingkat.text = newsList[position].deskripsi_singkat
        holder.binding.txtTitle.text = newsList[position].title

        holder.binding.btnRead.setOnClickListener {
            val action = MainFragmentDirections.actionNewsDatailFragment(newsList[position].id.toString())
            Navigation.findNavController(it).navigate(action)
        }

        val picasso = Picasso.Builder(holder.itemView.context)
        picasso.listener { picasso, uri, exception ->
            exception.printStackTrace()
        }

        picasso.build().load(newsList[position].photo_url).into(holder.binding.imageView, object :
            Callback {
            override fun onSuccess() {
                holder.binding.progressBar.visibility = View.INVISIBLE
                holder.binding.imageView.visibility = View.VISIBLE
            }

            override fun onError(e: Exception?) {
                Log.e("Picasso_error", "ERROR")
            }

        })

        var imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)
        var progressBar = holder.itemView.findViewById<ProgressBar>(R.id.progressBar)
        imageView.loadImage(newsList[position].photo_url, progressBar)
    }

    fun updateNewsList(newNewsList: ArrayList<News>) {
        newsList.clear()
        newsList.addAll(newNewsList)
    }
}