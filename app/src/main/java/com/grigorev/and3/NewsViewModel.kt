package com.grigorev.and3

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    fun loadNews(category: String) {
        var articles: List<Article>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.emit(State.Loading)
                articles = Api.request.getNews(category).articles
                _state.emit(State.Content(articles))
            } catch (e: Exception) {
                _state.emit(State.Error("$e"))
            }
        }
    }
}