package com.grigorev.and3

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var authorsList = mutableListOf<String>()
    private var titlesList = mutableListOf<String>()
    private var publishedAtList = mutableListOf<String>()
    private var sourceNamesList = mutableListOf<String>()
    private var descriptionsList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        makeAPIRequest()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
        }

    }

    private fun fillLists(
        author: String,
        title: String,
        publishedAt: String,
        sourceNames: String,
        descriptions: String,
    ) {
        authorsList.add(author)
        titlesList.add(title)
        publishedAtList.add(publishedAt)
        sourceNamesList.add(sourceNames)
        descriptionsList.add(descriptions)
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
                    fillLists(
                        article.author,
                        article.title,
                        article.publishedAt,
                        article.source.name,
                        article.description
                    )
                }

                withContext(Dispatchers.Main) {
                    val adapter = NewsAdapter(
                        authorsList,
                        titlesList,
                        publishedAtList,
                        sourceNamesList,
                        descriptionsList
                    )
                    binding.newsList.adapter = adapter
                }

            } catch (e: Exception) {
                Log.e("MainActivity", e.toString())
            }
        }
    }
}