package com.submission.dicoding.dgitapp.ui.detail

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
    private val followers = MutableLiveData<List<UserItems>>()
    private val followings = MutableLiveData<List<UserItems>>()
    private val loading = MutableLiveData(true)
    private val token = "token " + BuildConfig.API_KEY

    fun userFollowers(username: String) {
        loading.value = true
        val client = ApiConfig.getApiService().getFollowers(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    followers.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun userFollowings(username: String) {
        loading.value = true
        val client = ApiConfig.getApiService().getFollowings(username, token)
        client.enqueue(object : Callback<List<UserItems>> {
            override fun onResponse(
                call: Call<List<UserItems>>,
                response: Response<List<UserItems>>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    followings.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserItems>>, t: Throwable) {
                loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(): LiveData<List<UserItems>> {
        return followers
    }

    fun getFollowings(): LiveData<List<UserItems>> {
        return followings
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }
}