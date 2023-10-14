package com.example.github.Fragment

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github.Adapter.FollowerAdapter
import com.example.github.DetailActivity
import com.example.github.Model.FollowData
import com.example.github.R
import com.example.github.View.FollowViewModel
import com.example.github.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {
    companion object {
        const val ARG_POSITION: String = "position"
        const val ARG_USERNAME: String = "username"
        private const val TAG = "FollowFragment"
    }

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: FollowerAdapter
    private lateinit var viewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        viewModel = ViewModelProvider(this)[FollowViewModel::class.java]

        val layoutManager = LinearLayoutManager(activity)
        binding.lvUserName.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.lvUserName.addItemDecoration(itemDecoration)

        val position = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)

        when (position) {
            0 -> {
                viewModel.fetchGitHubUserFollower(username.toString())
            }
            else -> {
                viewModel.fetchGitHubUserFollowing(username.toString())
            }
        }

        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        viewModel.userListFollow.observe(viewLifecycleOwner) { userList ->
            adapter = userList?.let { FollowerAdapter(it) }!!
            binding.lvUserName.adapter = adapter
            adapter.setOnItemClickCallback(object : FollowerAdapter.OnItemClickCallback {

                override fun onItemClicked(data: FollowData) {
                    Log.d(ContentValues.TAG, "onItemClicked: ${data.login}")

                    var i = Intent(requireActivity(), DetailActivity::class.java)
                    i.putExtra("username", data.login)
                    i.putExtra("id", data.id)
                    i.putExtra("avatar", data.avatar_url)
                    startActivity(i)
                }
            })
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
