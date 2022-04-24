package com.example.First_App.view


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.First_App.R
import com.example.First_App.view.adapters.CustomAdapter
import com.example.First_App.viewmodel.MoviesActivityViewModel

class MoviesActivity : AppCompatActivity() {

    private val mViewModel: MoviesActivityViewModel = MoviesActivityViewModel()

    private lateinit var mMoviesRecycler: RecyclerView
    private lateinit var mMoviesAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        initViews()
        initObservers()
        mViewModel.getMovies()
    }

    private fun initObservers() {
        mViewModel.apply {
            movies.observe(this@MoviesActivity) {
                mMoviesAdapter = CustomAdapter(it, this@MoviesActivity)
                mMoviesRecycler.adapter = mMoviesAdapter
            }
        }
    }

    private fun initViews() {
        mMoviesRecycler = findViewById(R.id.recyclerview)
        mMoviesRecycler.layoutManager = GridLayoutManager(this, 2)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

     fun onItemClick(position: Int) {
        val intent = Intent(this@MoviesActivity, MoviesDetailsActivity::class.java)
        intent.putExtra("id", position)
        startActivity(intent)
    }
}





