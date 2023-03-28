package com.grigorev.and3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.grigorev.and3.databinding.NewsItemBinding

class NewsAdapter(
    private val authors: List<String>,
    private val titles: List<String>,
    private val publishedAt: List<String>
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val itemAuthor = binding.author
        val itemTitle = binding.title
        val itemPublishedAt = binding.publishedAt

        val newsItem = binding.newsItem

        init {
            newsItem.setOnClickListener { v: View ->
                //реализовать переход на фрагмент с новостью
                //val position: Int = bindingAdapterPosition
                //val intent = Intent(Intent.ACTION_VIEW)
                //intent.data = Uri.parse(titles[position])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.itemAuthor.text = authors[position]
        holder.itemTitle.text = titles[position]
        holder.itemPublishedAt.text = publishedAt[position]
    }

    override fun getItemCount(): Int = titles.size
}