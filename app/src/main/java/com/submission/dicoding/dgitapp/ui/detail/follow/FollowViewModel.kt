package com.submission.dicoding.dgitapp.ui.detail.follow

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _followers = MutableLiveData<List<UserItems>>(null)
    private val _followings = MutableLiveData<List<UserItems>>(null)
    val followers: LiveData<List<UserItems>> = _followers
    val followings: LiveData<List<UserItems>> = _followings
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading
    private val token = "token " + BuildConfig.API_KEY

    fun userFollowers(username: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().getFollowers(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _followers.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                _loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun userFollowings(username: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().getFollowings(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _followings.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                _loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}