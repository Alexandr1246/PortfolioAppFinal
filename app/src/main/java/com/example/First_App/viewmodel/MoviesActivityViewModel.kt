package com.example.First_App.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.First_App.data.MovieDetails
import com.example.First_App.data.Movies
import com.example.First_App.model.repository.MovieDBRepository
import com.example.First_App.model.repository.MovieDBRepositoryImpl
import com.example.First_App.data.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivityViewModel {

    private val _movies = MutableLiveData<List<Result>?>()
    val movies: LiveData<List<Result>?> = _movies

    private val _moviesDetails = MutableLiveData<MovieDetails>()
    val movieDetails: LiveData<MovieDetails> = _moviesDetails

    private val mMoviesRepository: MovieDBRepository = MovieDBRepositoryImpl()

    fun getMovies() {

        val response = mMoviesRepository.getMovies()
        response.enqueue(object : Callback<Movies> {
            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                _movies.postValue(response.body()?.results)
            }

            override fun onFailure(call: Call<Movies>, t: Throwable) {
                Log.d("testLog", "onFailure ${t.message}")
            }
        })

    }

    fun getMoviesDetails(id: Int){

        val response = mMoviesRepository.getMovieDetails(id)
        response.enqueue(object : Callback<MovieDetails>{
            override fun onResponse(call: Call<MovieDetails>, response: Response<MovieDetails>) {
                _moviesDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                Log.d("testLog", "onFailure ${t.message}")
            }

            })
        }
    }