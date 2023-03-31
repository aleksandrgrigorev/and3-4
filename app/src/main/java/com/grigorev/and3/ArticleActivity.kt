package com.grigorev.and3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.grigorev.and3.databinding.ActivityArticleBinding
import com.squareup.picasso.Picasso

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.customToolbar
        setSupportActionBar(toolbar)

        val title = binding.newsTitle
        val sourceName = binding.sourceName
        val description = binding.description
        val further = binding.furtherReading
        val glideImage = binding.glideImage
        val picassoImage = binding.picassoImage

        val mTitle = intent?.getStringExtra(TITLE)
        val mSourceName = intent?.getStringExtra(SOURCE_NAME)
        val mDescription = intent?.getStringExtra(DESCRIPTION)
        val mUrl = intent?.getStringExtra(NEWS_URL)
        val mImage = intent?.getStringExtra(IMAGE_URL)

        title.text = mTitle
        sourceName.text = getString(R.string.source_name, mSourceName)
        description.text = mDescription
        further.text = getString(R.string.url, mUrl)

        Glide.with(this)
            .load(mImage)
            .into(glideImage)

        Picasso.get()
            .load(mImage)
            .into(picassoImage)
    }
}