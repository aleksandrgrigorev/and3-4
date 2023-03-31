package com.grigorev.and3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/v2/"
const val API_KEY = "17e5c12b76344b1fa54a7fb5ec177150"
const val DATE = "today"
const val NEWS_NUMBER = "20"

interface Api {

    @GET("everything?from=$DATE&sortBy=popularity&pageSize=$NEWS_NUMBER&apiKey=$API_KEY")
    suspend fun getNews(@Query("q") category: String): NewsApiResponse

    companion object {
        val request: Api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}