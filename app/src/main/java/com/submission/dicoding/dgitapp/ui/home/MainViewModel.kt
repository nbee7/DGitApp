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
    private val listUserSearch = MutableLiveData<List<UserItems>>()
    private val loading = MutableLiveData(true)
    private val token = "token " + BuildConfig.API_KEY
    private val userDummy = "brian"

    init {
        searchUser(userDummy)
    }

    fun searchUser(username: String) {
        loading.value = true
        val client = ApiConfig.getApiService().searchUser(username, token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    listUserSearch.value = response.body()?.items
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getListSearch(): LiveData<List<UserItems>> {
        return listUserSearch
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }
}