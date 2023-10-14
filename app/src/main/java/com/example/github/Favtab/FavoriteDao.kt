package com.example.github.Favtab

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.Favtab.FavoriteUser

@Dao
interface FavoriteUserDao {
    @Query("SELECT * FROM favorites")
    fun getAllFavorites(): List<FavoriteUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favoriteUser: FavoriteUser)

    @Delete
    fun deleteFavorite(favoriteUser: FavoriteUser)
}