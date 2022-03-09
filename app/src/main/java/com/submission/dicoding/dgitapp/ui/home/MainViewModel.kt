package com.submission.dicoding.dgitapp.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listUserSearch = MutableLiveData<List<UserItems>>()
    val listUserSeach: LiveData<List<UserItems>> = _listUserSearch
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading
    private val token = "token " + BuildConfig.API_KEY
    private val userDummy = "brian"

    init {
        searchUser(userDummy)
    }

    fun searchUser(username: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().searchUser(username, token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _listUserSearch.value = response.body()?.items
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}