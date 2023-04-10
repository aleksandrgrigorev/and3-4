package com.grigorev.and3

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("everything?sortBy=publishedAt&language=en&pageSize=20&apiKey=${BuildConfig.API_KEY}")
    suspend fun getNews(@Query("q") category: String): NewsApiResponse

    companion object {
        val apiClient: Api = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)
    }
}