package com.example.First_App.model.repository

import com.example.First_App.Constants
import com.example.First_App.data.MovieDetails
import com.example.First_App.data.Movies
import com.example.First_App.model.apis.ApiInterface
import retrofit2.Call

class MovieDBRepositoryImpl : MovieDBRepository {
    private val apiInterface = ApiInterface.create()
    override fun getMovies(): Call<Movies> {
        return apiInterface.getMovies(Constants.API_KEY, "en_US", 1)
    }

    override fun getMovieDetails(id: Int): Call<MovieDetails> {
        return apiInterface.getMovieDetails(id, Constants.API_KEY)
    }

}