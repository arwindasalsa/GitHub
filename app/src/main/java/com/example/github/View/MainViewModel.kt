package com.example.github.View

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.Interfaces.GithubAPI
import com.example.github.Model.DetailUserData
import com.example.github.Model.SearchResponse
import com.example.github.Model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private val _userListSearch = MutableLiveData<List<UserData?>>()
    val userListSearch: MutableLiveData<List<UserData?>> get() = _userListSearch

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    init {
        fetchGitHubUserSearch("arwinda")
    }

    companion object {
        private const val TAG = "MainViewModel"
    }

    fun fetchGitHubUserSearch(userLogin: String) {
        _loading.value = true
        val GithubService = GithubAPI.service
        val call = GithubService.getSearchUsers(userLogin)

        call.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    val searchResponse = response.body()
                    val userList = searchResponse?.items ?: emptyList()

                    val usersToFetch = userList.take(15)

                    for (user in usersToFetch) {
                        user?.login?.let { userId ->
                            fetchGithubUserDetail(userId) {             }
                        }
                    }

                    CoroutineScope(Dispatchers.Main).launch {
                        delay(1000)
                        _userListSearch.value = usersToFetch
                        _loading.value = false
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _loading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun fetchGithubUserDetail(userId: String, callback: () -> Unit) {
        val GithubService = GithubAPI.service
        val call = GithubService.getUserData(userId)

        call.enqueue(object : Callback<DetailUserData> {
            override fun onResponse(call: Call<DetailUserData>, response: Response<DetailUserData>) {
                if (response.isSuccessful) {
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserData>, t: Throwable) {
                Log.e(TAG, "onFailure in fetchGithubUserDetail for user $userId: ${t.message}")
            }
        })
    }
}
