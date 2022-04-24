package com.example.First_App.data

import com.example.First_App.data.Result

data class Movies(
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)