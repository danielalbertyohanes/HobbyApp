package com.example.hobbyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hobbyapp.R
import com.example.hobbyapp.databinding.FragmentLoginBinding
import com.example.hobbyapp.databinding.FragmentMainBinding
import com.example.hobbyapp.viewmodel.ListNewsViewModel


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel:ListNewsViewModel
    private val newsListAdapter  = NewsListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListNewsViewModel::class.java)
        viewModel.getNews()

        binding.recView.layoutManager = LinearLayoutManager(context)
        binding.recView.adapter = newsListAdapter

        observeViewModel()

        binding.refreshLayout.setOnRefreshListener {
            binding.recView.visibility = View.GONE
            binding.txtError.visibility = View.GONE
            binding.progressLoad.visibility = View.VISIBLE
            viewModel.getNews()
            binding.refreshLayout.isRefreshing = false
        }

    }

    fun observeViewModel() {
        viewModel.listNewsLD.observe(viewLifecycleOwner, Observer {
            newsListAdapter.updateNewsList(it)
        })
        viewModel.newsLoadErrorLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
            }
        })
        viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                binding.recView.visibility = View.GONE
                binding.progressLoad.visibility = View.VISIBLE
            } else {
                binding.recView.visibility = View.VISIBLE
                binding.progressLoad.visibility = View.GONE
            }
        })
    }

}