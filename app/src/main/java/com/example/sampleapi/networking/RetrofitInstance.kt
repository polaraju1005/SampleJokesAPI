package com.example.sampleapi.networking

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.sampleapi.model.Response
import com.example.sampleapi.utils.AppUtils.BASE_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    fun getJokes(context: Context, callback: (Response) -> Unit) {
        val retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()

        val service = retrofit.create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jokes = service.getJokesApi()
                CoroutineScope(Dispatchers.Main).launch {
                    Log.i("JOKES","$jokes")
                    callback(jokes)
                }
            } catch (e: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "Api Call Failed", Toast.LENGTH_SHORT).show()
                }
                Log.e("API_EXCEPTION", "$e")
            }
        }
    }
}