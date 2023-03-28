package com.grigorev.and3

import com.grigorev.and3.api.NewsApiJSON
import retrofit2.http.GET

interface ApiRequest {

    @GET("everything?q=software&from=today&sortBy=popularity&pageSize=20&apiKey=17e5c12b76344b1fa54a7fb5ec177150")
    suspend fun getNews(): NewsApiJSON
}