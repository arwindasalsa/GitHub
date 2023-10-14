package com.example.github.View

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.Interfaces.GithubAPI
import com.example.github.Model.FollowData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    private val _userListFollow = MutableLiveData<List<FollowData>?>()
    val userListFollow: LiveData<List<FollowData>?> get() = _userListFollow

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    fun fetchGitHubUserFollower(userId: String){
        _loading.value = true
        val GithubService = GithubAPI.service
        val call = GithubService.getFollowers(userId)
        call.enqueue(object : Callback<List<FollowData>> {
            override fun onResponse(
                call: Call<List<FollowData>>,
                response: Response<List<FollowData>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    val followResponse = response.body()
                    _userListFollow.postValue(followResponse)

                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowData>>, t: Throwable) {
                _loading.value = false
                Log.e("FollowViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun fetchGitHubUserFollowing(userId: String){
        _loading.value = true
        val GithubService = GithubAPI.service
        val call = GithubService.getFollowing(userId)
        call.enqueue(object : Callback<List<FollowData>> {
            override fun onResponse(
                call: Call<List<FollowData>>,
                response: Response<List<FollowData>>
            ) {
                _loading.value = false
                if (response.isSuccessful){
                    val followResponse = response.body()
                    _userListFollow.postValue(followResponse)

                } else {
                    Log.e("FollowViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowData>>, t: Throwable) {
                _loading.value = false
                Log.e("FollowViewModel", "onFailure: ${t.message}")
            }
        })
    }
}