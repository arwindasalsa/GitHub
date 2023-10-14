package com.example.github.View

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.Interfaces.GithubAPI
import com.example.github.Model.DetailUserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel(){

    private val _userDetail = MutableLiveData<DetailUserData?>()
    val userDetail: LiveData<DetailUserData?> = _userDetail

    fun fetchGitHubUserDetail(username: String){
        val GithubService = GithubAPI.service
        val call = GithubService.getUserData(username)
        call.enqueue(object : Callback<DetailUserData> {
            override fun onResponse(
                call: Call<DetailUserData>,
                response: Response<DetailUserData>
            ) {
                if (response.isSuccessful){
                    val detailResponse = response.body()
                    _userDetail.postValue(detailResponse)

                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserData>, t: Throwable) {
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }
        })
    }
}