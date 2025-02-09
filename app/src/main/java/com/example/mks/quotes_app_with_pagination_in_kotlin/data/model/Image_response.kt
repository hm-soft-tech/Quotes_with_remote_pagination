package com.example.mks.quotes_app_with_pagination_in_kotlin.data.model

data class Image_response(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)