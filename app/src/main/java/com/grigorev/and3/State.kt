package com.grigorev.and3

sealed class State {
    object Loading : State()
    data class Error(val error: String) : State()
    data class Content(val news: List<Article>) : State()
}