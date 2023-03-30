package com.grigorev.and3

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {
    var news = MutableLiveData<List<Article>>()

    fun loadNews() {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                articles = Api.request.getNews().articles
            } catch (e: Exception) {
                articles = emptyList()
            }
            news.postValue(articles)
        }
    }
}