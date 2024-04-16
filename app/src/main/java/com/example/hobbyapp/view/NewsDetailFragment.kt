package com.example.hobbyapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentNewsDetailBinding
import com.example.hobbyapp.databinding.FragmentProfileBinding
import com.example.hobbyapp.model.News
import com.example.hobbyapp.util.loadImage
import com.example.hobbyapp.viewmodel.DetailNewsViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var viewModel: DetailNewsViewModel
    private var currentPage = 0
    private var totalPages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsId = arguments?.getString("newsId")
        viewModel = ViewModelProvider(this).get(DetailNewsViewModel::class.java)
        viewModel.getNewsDetail(newsId.toString())

        observeViewModel()

        binding.btnNext?.setOnClickListener {
            if (currentPage < totalPages - 1) {
                currentPage++
                updatePageContent()
            }
        }

        binding.btnBack?.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                updatePageContent()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.newsLD.observe(viewLifecycleOwner, Observer { newsData ->
            newsData?.let {
                totalPages = it.content?.split("\n")?.size ?: 0
                updatePageContent()
            }
        })
    }

    private fun updatePageContent() {
        viewModel.newsLD.value?.let { newsData ->
            val contentPages = newsData.content?.split("\n")
            if (currentPage == 0) {
                with(binding) {
                    txtTitle.text = newsData.title
                    txtAuthor.text = "@"+newsData.author
                    txtSubjudul.text = newsData.sub_title
                    imageView.loadImage(newsData.photo_url, progressBar)
                }
            }
            binding.txtContent.text = contentPages?.get(currentPage)
        }
    }
}
