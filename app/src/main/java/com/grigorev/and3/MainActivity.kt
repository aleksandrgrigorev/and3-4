package com.grigorev.and3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grigorev.and3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var newsCategories: List<String>
    private lateinit var selectedCategory: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.customToolbar
        setSupportActionBar(toolbar)

        newsCategories = resources.getStringArray(R.array.categories_array).toList()
        selectedCategory = newsCategories.first()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsCategories)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.newsCategorySpinner.apply {
            onItemSelectedListener = this@MainActivity
            adapter = arrayAdapter
        }

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.loadNews(selectedCategory)
        newsViewModel.news.observe(this) { articles ->
            val adapter = NewsAdapter(articles)
            binding.newsList.adapter = adapter

            swipeRefreshLayout = binding.swipeRefreshLayout

            swipeRefreshLayout.setOnRefreshListener {
                swipeRefreshLayout.isRefreshing = true

                try {
                    newsViewModel.loadNews(selectedCategory)
                    Toast.makeText(this, "Page refreshed", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    AlertDialog.Builder(this)
                        .setTitle("Error")
                        .setMessage("$e")
                        .create()
                        .show()
                }
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        parent.selectedItem.toString().let { it ->
            selectedCategory = it
            newsViewModel.loadNews(it)
            val categoryName = it.replaceFirstChar { it.uppercase() }
            binding.customToolbar.title = categoryName
            Toast.makeText(this, "Showing news in category: $categoryName", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}