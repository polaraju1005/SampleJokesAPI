package com.example.sampleapi

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.sampleapi.databinding.ActivityMainBinding
import com.example.sampleapi.networking.RetrofitInstance.getJokes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGetJoke.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            getJokeFromApi()
        }
    }

    private fun getJokeFromApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getJokes(this@MainActivity) { jokes ->
                    runOnUiThread {
                        binding.progressBar.visibility = View.GONE
                        binding.txtJoke.text = jokes.value

                        Glide.with(this@MainActivity).load(jokes.icon_url).into(binding.imgAPI)
                    }
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    runOnUiThread {
                        binding.txtJoke.text = "Failed to load jokes"
                        binding.progressBar.visibility = View.GONE
                    }
                    Log.e("Exception In Fetching Response", "$e")
                }
            }
        }
    }
}