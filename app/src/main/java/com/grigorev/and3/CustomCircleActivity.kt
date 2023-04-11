package com.grigorev.and3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grigorev.and3.databinding.CustomCircleViewBinding

class CustomCircleActivity : AppCompatActivity() {

    private lateinit var binding: CustomCircleViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_circle_view)

        binding = CustomCircleViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.customToolbar
        setSupportActionBar(toolbar)
    }
}