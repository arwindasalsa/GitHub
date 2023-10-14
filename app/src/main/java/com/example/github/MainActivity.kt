package com.example.github

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.github.Adapter.HomeAdapter
import com.example.github.Interfaces.GithubAPI
import com.example.github.Model.SearchResponse
import com.example.github.Model.UserData
import com.example.github.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.github.FavoriteActivity


class MainActivity : AppCompatActivity() {

    private lateinit var bd: ActivityMainBinding
    private lateinit var adapter: HomeAdapter
    var names = arrayListOf<UserData>()
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bd.root)

        adapter = HomeAdapter(this, names)
        bd.lvUserName.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserData) {
                Log.d(TAG, "onItemClicked: ${data.login}")

                var i = Intent(this@MainActivity, DetailActivity::class.java)
                i.putExtra("username", data.login)
                i.putExtra("id", data.id)
                i.putExtra("avatar", data.avatar_url)
                startActivity(i)
            }
        })

        progressBar = findViewById(R.id.progressBar)

        GithubAPI.service.getListUsers().enqueue(object : Callback<List<UserData>> {
            override fun onResponse(
                call: retrofit2.Call<List<UserData>>,
                response: retrofit2.Response<List<UserData>>
            ) {
                hideProgressBar()

                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        for (i in data) {
                            names.add(i)
                        }
                        bd.lvUserName.adapter = adapter
                    }
                }
            }

            override fun onFailure(call: retrofit2.Call<List<UserData>>, t: Throwable) {
                println("Error: ${t.message}")
                hideProgressBar()
            }
        })

        bd.svUserName.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                var result = p0
                Log.d(TAG, "onQueryTextSubmit: $result")
                searchUser(result!!)
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        // Tombol "Favorite" untuk menuju halaman favorit
        val toggfavoriteButton = findViewById<Button>(R.id.toggleFavorite)

        toggfavoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun searchUser(uname: String) {
        showProgressBar()
        GithubAPI.service.getSearchUsers(uname).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                hideProgressBar()

                if (response.isSuccessful) {
                    val data = response.body()!!.items
                    names.clear()
                    if (data != null) {
                        for (i in data) {
                            names.add(i)
                        }
                        bd.lvUserName.adapter = adapter
                        adapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                println("Error: ${t.message}")
                hideProgressBar()
            }
        })
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}