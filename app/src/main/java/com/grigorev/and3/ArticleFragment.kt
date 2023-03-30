package com.grigorev.and3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.grigorev.and3.databinding.FragmentArticleBinding
import com.squareup.picasso.Picasso

class ArticleFragment : Fragment() {
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleBinding.inflate(inflater, container, false)

        val title = binding.title
        val sourceName = binding.sourceName
        val description = binding.description
        val glideImage = binding.glideImage
        val picassoImage = binding.picassoImage

        val bundle = arguments
        val mTitle = bundle?.getString("title")
        val mSourceName = bundle?.getString("sourceName")
        val mDescription = bundle?.getString("description")
        val mImage = bundle?.getString("urlToImage")

        title.text = mTitle
        sourceName.text = getString(R.string.source_name, mSourceName)
        description.text = mDescription

        Glide.with(this)
            .load(mImage)
//            .placeholder(R.drawable.ic_launcher_foreground)
            .into(glideImage)

        Picasso.get()
            .load(mImage)
            .into(picassoImage)

        return binding.root
    }
}