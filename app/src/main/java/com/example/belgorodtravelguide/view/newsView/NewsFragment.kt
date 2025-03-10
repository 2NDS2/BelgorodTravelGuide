package com.example.belgorodtravelguide.view.newsView

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.data.db.AppDatabase
import com.example.belgorodtravelguide.databinding.FragmentNewsBinding
import com.example.belgorodtravelguide.viewModel.newsVM.NewsViewModel
import com.example.belgorodtravelguide.viewModel.newsVM.factory.NewsViewModelFactory

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var newsAdapter: NewsAdapter
    private var currentPage: String? = null
    private var isLoading = false
    private lateinit var viewModel: NewsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        val ViewModelFactory = NewsViewModelFactory(AppDatabase.getInstance(requireContext()),requireContext())
        viewModel = ViewModelProvider(this,ViewModelFactory).get(NewsViewModel::class.java)

        setupRecyclerView()
        observeViewModel()
        showContent()
        return binding.root
    }

    private fun showContent() {
        //проверка наличия интернета
        if (viewModel.isInternetAvailable(requireContext())) {
            //интернет есть, очищаем бд и загружаем новости с сервера
            viewModel.clearDatabase()
            viewModel.fetchNews()
        } else {
            //интернета нет, загружаем новости из бд
            Toast.makeText(requireContext(), "Нет подключения к интернету", Toast.LENGTH_SHORT).show()
            viewModel.loadNewsFromDatabase()
        }
    }

    private fun observeViewModel() {
        viewModel.news.observe(viewLifecycleOwner) { newsList ->
            newsAdapter.setNews(newsList)
        }

        viewModel.currentPage.observe(viewLifecycleOwner) { page ->
            currentPage = page
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            isLoading = loading
            // надо БЫ добавить индикатор загрузки
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        newsAdapter = NewsAdapter { url -> openNewsInBrowser(url) }
        binding.recyclerView.adapter = newsAdapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (!isLoading && lastVisibleItemPosition == layoutManager.itemCount - 1) {
                    // Достигнут конец списка
                    currentPage?.let { viewModel.fetchNews(it) }
                }
            }
        })
    }

    private fun openNewsInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}