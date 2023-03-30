package com.grigorev.and3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.grigorev.and3.databinding.NewsItemBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(
    private val articles: List<Article>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val itemAuthor = binding.author
        val itemTitle = binding.title
        val itemPublishedAt = binding.publishedAt

        val newsItem = binding.newsItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val article = articles[position]
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ENGLISH)
        val publishedAtFormatted = parser.parse(article.publishedAt)?.let { formatter.format(it) }

        holder.apply {

            itemAuthor.text = article.author
            itemTitle.text = article.title
            itemPublishedAt.text = publishedAtFormatted

            newsItem.setOnClickListener { v ->
                val activity = v?.context as AppCompatActivity
                val articleFragment = ArticleFragment()

                val articleFragmentBundle = Bundle()
                articleFragmentBundle.putString("title", article.title)
                articleFragmentBundle.putString("sourceName", article.source.name)
                articleFragmentBundle.putString("description", article.description)
                articleFragmentBundle.putString("urlToImage", article.urlToImage)
                articleFragment.arguments = articleFragmentBundle

                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, articleFragment).addToBackStack(null).commit()
            }
        }
    }

    override fun getItemCount(): Int = articles.size
}