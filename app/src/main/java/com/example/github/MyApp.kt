package com.example.github

import android.app.Application
import androidx.room.Room
import com.example.github.Favtab.AppDatabase

class MyApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        // Inisialisasi database di sini
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "favorite-database"
        ).build()
    }
}