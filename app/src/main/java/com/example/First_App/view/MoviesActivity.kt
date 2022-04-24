package com.example.First_App.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.First_App.Constants.API_KEY
import com.example.First_App.CustomAdapter
import com.example.First_App.ItemsViewModel
import com.example.First_App.R
import com.example.First_App.data.Movies
import com.example.First_App.model.apis.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        Log.d("testLog", "MoviesActivity start activity")
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = GridLayoutManager(this, 2)
        val data = ArrayList<ItemsViewModel>()
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.common_full_open_on_phone, "Item+i"))
        }
        val apiInterface = ApiInterface.create().getMovies(API_KEY)

        apiInterface.enqueue(object : Callback<Movies>, CustomAdapter.ItemClickListener {
            override fun onResponse(call: Call<Movies>?, response: Response<Movies>?) {
                Log.d(
                    "testLog",
                    "OnResponse Success ${call.toString()} ${response?.body()?.results?.size}"
                )
                val adapter = CustomAdapter(response?.body()?.results, this)
                recyclerview.adapter = adapter
            }

            override fun onFailure(call: Call<Movies>?, t: Throwable?) {
                Log.d("testLog", "onFailure : ${t?.message}")
            }

            override fun onItemClick(id: Int) {
                val intent = Intent(this@MoviesActivity, MoviesDetailsActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
        Log.d("testLog", "MoviesActivity application been closed")
    }

}