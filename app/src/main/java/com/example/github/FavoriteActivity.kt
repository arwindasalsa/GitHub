package com.example.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.github.Adapter.FavoriteAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter
    private lateinit var databaseManager: DatabaseManager // Sesuaikan dengan nama kelas yang sesuai

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        recyclerView = findViewById(R.id.rvFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = FavoriteAdapter()
        recyclerView.adapter = adapter

        databaseManager = DatabaseManager(this)

        val favoriteUsers = databaseManager.getAllFavoriteUsers()

        adapter.setData(favoriteUsers)
    }
}