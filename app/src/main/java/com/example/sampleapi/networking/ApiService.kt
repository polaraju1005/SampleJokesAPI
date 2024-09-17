package com.example.sampleapi.networking

import com.example.sampleapi.model.Response
import retrofit2.http.GET

interface ApiService {
    @GET("jokes/random")
    suspend fun getJokesApi(): Response
}