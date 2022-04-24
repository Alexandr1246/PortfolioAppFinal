package com.example.First_App.model.repository

import com.example.First_App.data.MovieDetails
import com.example.First_App.data.Movies
import retrofit2.Call

interface MovieDBRepository {
    fun getMovies(): Call<Movies>
    fun getMovieDetails(id: Int): Call<MovieDetails>
}