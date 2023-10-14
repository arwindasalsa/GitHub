package com.example.github.Adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.github.Fragment.FollowFragment

class SectionsPagerAdapter(activity: AppCompatActivity, username: String) : FragmentStateAdapter(activity) {

    var name = username

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position)
            putString(FollowFragment.ARG_USERNAME, name)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}