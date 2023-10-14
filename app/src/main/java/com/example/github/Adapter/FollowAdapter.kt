package com.example.github.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.github.Model.FollowData
import com.example.github.R

class FollowAdapter(private val context: Context, private val onClick: (FollowData) -> Unit) : ListAdapter<FollowData, FollowAdapter.FollowerViewHolder>(FollowDiffCallback()) {

    inner class FollowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFollowerName: TextView = itemView.findViewById(R.id.tv_follower_name)
        val imgFollowerAvatar: ImageView = itemView.findViewById(R.id.img_follower_avatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_follow, parent, false)
        return FollowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val follower = getItem(position)

        holder.tvFollowerName.text = follower.login

        Glide.with(context)
            .load(follower.avatar_url)
            .apply(RequestOptions().override(55, 55))
            .into(holder.imgFollowerAvatar)

        holder.itemView.setOnClickListener {
            onClick(follower)
        }
    }
}

class FollowDiffCallback : DiffUtil.ItemCallback<FollowData>() {
    override fun areItemsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FollowData, newItem: FollowData): Boolean {
        return oldItem == newItem
    }
}
