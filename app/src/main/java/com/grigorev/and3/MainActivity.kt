package com.grigorev.and3

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grigorev.and3.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

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

        val dialog = LoadingDialog(this)

        lifecycleScope.launch {
            newsViewModel.state.collect {
                when (it) {
                    is State.Loading -> {
                        dialog.startDialog()
                        newsViewModel.loadNews(selectedCategory)
                    }
                    is State.Content -> {
                        dialog.dismissDialog()
                        val categoryName =
                            selectedCategory.replaceFirstChar { it.uppercase() }
                        binding.customToolbar.title = categoryName
                        Toast.makeText(
                            this@MainActivity,
                            "Showing news in category: $categoryName",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        val adapter = NewsAdapter(articles = it.news)
                        binding.newsList.adapter = adapter

                        swipeRefreshLayout = binding.swipeRefreshLayout

                        swipeRefreshLayout.setOnRefreshListener {
                            swipeRefreshLayout.isRefreshing = true
                            newsViewModel.loadNews(selectedCategory)
                            swipeRefreshLayout.isRefreshing = false
                        }
                    }
                    is State.Error -> {
                        val alertDialog = AlertDialog.Builder(this@MainActivity)
                            .setTitle("Error")
                            .setMessage(it.error)
                        alertDialog.show()
                    }
                }

            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        parent.selectedItem.toString().let { categoryName ->
            selectedCategory = categoryName
            newsViewModel.loadNews(categoryName)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}