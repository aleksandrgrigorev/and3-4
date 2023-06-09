package com.grigorev.and3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
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
        
        setSupportActionBar(binding.customToolbar)

        newsCategories = resources.getStringArray(R.array.categories_array).toList()
        selectedCategory = newsCategories.first()

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsCategories)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.apply {
            newsCategorySpinner.apply {
                onItemSelectedListener = this@MainActivity
                adapter = arrayAdapter
            }
            customCircleActivityButton.setOnClickListener {
                this@MainActivity.startActivity(
                    Intent(this@MainActivity, CustomCircleActivity::class.java)
                )
            }
            mapsActivityButton.setOnClickListener {
                this@MainActivity.startActivity(
                    Intent(this@MainActivity, MapsActivity::class.java)
                )
            }
        }

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]

        val dialog = LoadingDialog(this)

        lifecycleScope.launch {
            newsViewModel.state.collect {
                when (it) {
                    is State.Loading -> {
                        dialog.startDialog()
                        newsViewModel.send(LoadNewsEvent(category = selectedCategory))
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
                        binding.newsList.addItemDecoration(
                            DividerItemDecoration(binding.newsList.context, DividerItemDecoration.VERTICAL)
                        )

                        swipeRefreshLayout = binding.swipeRefreshLayout

                        swipeRefreshLayout.setOnRefreshListener {
                            swipeRefreshLayout.isRefreshing = true
                            newsViewModel.send(LoadNewsEvent(category = selectedCategory))
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
            newsViewModel.send(LoadNewsEvent(category = selectedCategory))
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}