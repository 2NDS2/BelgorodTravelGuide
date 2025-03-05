package com.example.belgorodtravelguide.viewModel.newsVM

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.belgorodtravelguide.model.modelNews.api.NewsArticle
import com.example.belgorodtravelguide.model.modelNews.api.NewsResponse
import com.example.belgorodtravelguide.model.modelNews.api.RetrofitInstance
import com.example.belgorodtravelguide.model.modelNews.bd.NewsArticleEntity
import com.example.belgorodtravelguide.model.modelNews.bd.NewsDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel(private val newsDatabase: NewsDatabase, private val context: Context) : ViewModel() {

    //LiveData для хранения списка новостей
    private val _news = MutableLiveData<List<NewsArticle>>()
    val news: LiveData<List<NewsArticle>> get() = _news

    //LiveData для текущей страницы (для пагинации)
    private val _currentPage = MutableLiveData<String?>()
    val currentPage: LiveData<String?> get() = _currentPage

    //LiveData для отслеживания состояния загрузки
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    //для загрузки новостей по API
    fun fetchNews(page: String? = null) {
        _isLoading.value = true

        RetrofitInstance.api.getNews(
            "pub_71849c7d103e611dfaf3aa58b20a71c4a1601", //запасной ключ API
                  //"pub_7161479900d23567d2b4226f29a68b679f112", //основной ключ API
            "Белгород", // Запрос
            "ru", // Страна
            "ru", // Язык
            page // Страница (для пагинации)
        ).enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                _isLoading.value = false
                response.body()?.let { newsResponse ->
                    newsResponse.results.let {
                        _news.value = it
                        _currentPage.value = newsResponse.nextPage
                        saveNewsToDatabase(it)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                _isLoading.value = false
                // Можно здесь обработать ошибку
            }
        })
    }

    //сохранение новостей в бд
    private fun saveNewsToDatabase(newsList: List<NewsArticle>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newsEntities = newsList.map { article ->
                    NewsArticleEntity(
                        title = article.title,
                        description = article.description ?: "",
                        imageUrl = article.image_url,
                        url = article.link
                    )
                }
                newsDatabase.newsDao().insertAll(newsEntities)  // Вставляем все записи за один раз
            }
        }
    }

    //загрузка новостей из бд
    fun loadNewsFromDatabase() {
        viewModelScope.launch {
            val newsList = withContext(Dispatchers.IO) {
                newsDatabase.newsDao().getAllNews()
            }
            _news.value = newsList.map {
                NewsArticle(
                    it.title, it.description ?: "", it.imageUrl ?: "", it.url,
                    pubDate = "",
                    image_url = "",
                    source_name = ""
                )
            }
        }
    }

    //очистка бд
    fun clearDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                newsDatabase.newsDao().deleteAllNews()
            }
        }
    }

    //проверка интернет-соединения
    fun isInternetAvailable(requireContext: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
