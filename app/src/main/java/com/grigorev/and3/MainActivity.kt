package com.grigorev.and3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.grigorev.and3.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://newsapi.org/v2/"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var authorsList = mutableListOf<String>()
    private var titlesList = mutableListOf<String>()
    private var publishedAtList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeAPIRequest()
    }

    private fun addToList(author: String, title: String, publishedAt: String) {
        authorsList.add(author)
        titlesList.add(title)
        publishedAtList.add(publishedAt)
    }

    private fun makeAPIRequest() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getNews()

                for (article in response.articles) {
                    Log.i("MainActivity", "Result = $article")
                    addToList(article.author, article.title, article.publishedAt)
                }

                withContext(Dispatchers.Main) {
                    val adapter = NewsAdapter(authorsList, titlesList, publishedAtList)
                    binding.newsList.adapter = adapter
                }

            } catch (e: Exception) {
                Log.e("MainActivity", e.toString())
            }
        }
    }
}