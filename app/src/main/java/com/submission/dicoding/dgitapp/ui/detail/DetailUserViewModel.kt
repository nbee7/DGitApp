package com.submission.dicoding.dgitapp.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val detailUser = MutableLiveData<UserDetailResponse>()
    private val loading = MutableLiveData(true)
    private val token = "token " + BuildConfig.API_KEY

    fun detailUser(username: String) {
        loading.value = true
        val client = ApiConfig.getApiService().getDetail(username, token)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    detailUser.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getDetail(): LiveData<UserDetailResponse> {
        return detailUser
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }
}