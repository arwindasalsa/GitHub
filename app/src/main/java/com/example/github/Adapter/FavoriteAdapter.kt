package com.example.github.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.github.Favtab.FavoriteUser
import com.example.github.R

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val favoriteList = mutableListOf<FavoriteUser>() // Ganti dengan model data yang sesuai

    fun setData(data: List<FavoriteUser>) {
        favoriteList.clear()
        favoriteList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = favoriteList[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: FavoriteUser) {
        }
    }
}
