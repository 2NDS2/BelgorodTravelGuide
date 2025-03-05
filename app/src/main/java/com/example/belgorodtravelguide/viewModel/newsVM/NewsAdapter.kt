package com.example.belgorodtravelguide.viewModel.newsVM

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.belgorodtravelguide.databinding.ItemNewsBinding
import com.example.belgorodtravelguide.model.modelNews.api.NewsArticle
import com.squareup.picasso.Picasso

class NewsAdapter(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    private val newsList = mutableListOf<NewsArticle>()

    fun setNews(news: List<NewsArticle>) {
        val startPosition = newsList.size
        newsList.addAll(news)
        notifyItemRangeInserted(startPosition, news.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            onItemClick(article.link)  //передаем ссылку на статью
        }
    }

    override fun getItemCount(): Int = newsList.size

    class NewsViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: NewsArticle) {
            binding.titleTextView.text = article.title
            binding.descriptionTextView.text = article.description?: ""
            //проверка, если image_url == null?
            if (article.image_url.isNullOrBlank()) {
                binding.imageView.visibility = View.GONE
            } else {
                Picasso.get().load(article.image_url).into(binding.imageView)
            }
        }
    }
}
