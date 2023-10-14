package com.example.github.Favtab

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    val userId: String = 0,
    val username: String,
    val avatarUrl: String
)