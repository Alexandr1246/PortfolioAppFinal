package com.example.appdemo


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {        // вызов екрана регистрации
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        Log.d("testLog", "MoviesActivity start activity")
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        for (i in 1..20) {
            data.add(ItemsViewModel(R.drawable.common_full_open_on_phone, "Item " + i))
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
    }

    override fun onBackPressed() { // отвечает за то чтоб приложение закрывалось при нажатии кнопки назад
        super.onBackPressed()
        this.finishAffinity()
        Log.d("testLog", "MoviesActivity application been closed")
    }

}