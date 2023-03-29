package com.grigorev.and3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.grigorev.and3.databinding.NewsItemBinding
import java.text.SimpleDateFormat

class NewsAdapter(
    private val authors: List<String>,
    private val titles: List<String>,
    private val publishedAt: List<String>,
    private val sourceNames: List<String>,
    private val descriptions: List<String>,
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
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
        val publishedAtFormatted = parser.parse(publishedAt[position])?.let { formatter.format(it) }

        holder.itemAuthor.text = authors[position]
        holder.itemTitle.text = titles[position]
        holder.itemPublishedAt.text = publishedAtFormatted

        holder.newsItem.setOnClickListener { v ->
            val activity = v?.context as AppCompatActivity
            val articleFragment = ArticleFragment()

            val articleFragmentBundle = Bundle()
            articleFragmentBundle.putString("title", titles[position])
            articleFragmentBundle.putString("sourceName", sourceNames[position])
            articleFragmentBundle.putString("description", descriptions[position])
            articleFragment.arguments = articleFragmentBundle

            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, articleFragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int = titles.size
}