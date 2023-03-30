package com.grigorev.and3

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grigorev.and3.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        newsViewModel.loadNews()
        newsViewModel.news.observe(this) { articles ->
            val adapter = NewsAdapter(articles)
            binding.newsList.adapter = adapter
        }

        val loadingDialog = LoadingDialog(this@MainActivity)

        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true
            loadingDialog.startLoadingDialog()
            try {
                newsViewModel.loadNews()
            } catch (e: Exception) {
                loadingDialog.dismissDialog()
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("$e")
                    .show()
            }
            loadingDialog.dismissDialog()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}