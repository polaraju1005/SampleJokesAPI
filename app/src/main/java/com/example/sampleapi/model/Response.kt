package com.example.sampleapi.model

data class Response(
    val categories: List<Any>,
    val created_at: String,
    val icon_url: String,
    val id: String,
    val updated_at: String,
    val url: String,
    val value: String
)