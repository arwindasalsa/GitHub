package com.example.github.Favtab

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao
}