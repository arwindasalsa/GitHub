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

        // Inisialisasi DatabaseManager (atau sesuaikan dengan kelas yang sesuai)
        databaseManager = DatabaseManager(this)

        // Ambil data favorit dari database
        val favoriteUsers = databaseManager.getAllFavoriteUsers()

        // Set data ke adapter
        adapter.setData(favoriteUsers)
    }
}

