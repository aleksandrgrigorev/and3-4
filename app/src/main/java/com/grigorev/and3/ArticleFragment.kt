package com.grigorev.and3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grigorev.and3.databinding.FragmentArticleBinding

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

        val bundle = arguments
        val mTitle = bundle?.getString("title")
        val mSourceName = bundle?.getString("sourceName")
        val mDescription = bundle?.getString("description")

        title.text = mTitle
        sourceName.text = getString(R.string.source_name, mSourceName)
        description.text = mDescription

        return binding.root
    }
}