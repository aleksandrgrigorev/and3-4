package com.grigorev.and3

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grigorev.and3.databinding.NewsItemBinding
import java.text.SimpleDateFormat
import java.util.*

const val TITLE = "title"
const val SOURCE_NAME = "sourceName"
const val DESCRIPTION = "description"
const val IMAGE_URL = "imageUrl"
const val NEWS_URL = "newsUrl"

class NewsAdapter(
    private val articles: List<Article>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

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

            newsItem.setOnClickListener {
                val articleActivityIntent = Intent(newsItem.context, ArticleActivity::class.java)
                articleActivityIntent
                    .putExtra(TITLE, article.title)
                    .putExtra(SOURCE_NAME, article.source.name)
                    .putExtra(DESCRIPTION, article.description)
                    .putExtra(IMAGE_URL, article.urlToImage)
                    .putExtra(NEWS_URL, article.url)

                newsItem.context.startActivity(articleActivityIntent)
            }
        }
    }

    override fun getItemCount(): Int = articles.size
}