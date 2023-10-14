package com.example.github

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import com.example.github.Favtab.FavoriteUser

class DatabaseManager(context: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(context)

    fun addFavoriteUser(username: String, avatarUrl: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put("username", username)
        values.put("avatar_url", avatarUrl)

        try {
            db.insertOrThrow("favorite_users", null, values)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun removeFavoriteUser(username: String) {
        val db = dbHelper.writableDatabase
        db.delete("favorite_users", "username=?", arrayOf(username))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllFavoriteUsers(): List<FavoriteUser> {
        val db = dbHelper.readableDatabase
        val users = mutableListOf<FavoriteUser>()

        val cursor: Cursor? = db.rawQuery("SELECT * FROM favorite_users", null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val id = it.getInt(it.getColumnIndex("id"))
                    val username = it.getString(it.getColumnIndex("username"))
                    val avatarUrl = it.getString(it.getColumnIndex("avatar_url"))
                    val user = FavoriteUser(id, username, avatarUrl)
                    users.add(user)
                } while (it.moveToNext())
            }
        }
        cursor?.close()
        db.close()

        return users
    }
}
