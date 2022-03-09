package com.submission.dicoding.dgitapp.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryViewModel: ViewModel() {
    private val repository = MutableLiveData<List<UserRepositoryResponse>>()
    private val loading = MutableLiveData(true)
    private val token = "token " + BuildConfig.API_KEY

    fun userRepository(username: String) {
        loading.value = true
        val client = ApiConfig.getApiService().getRepository(username, token)
        client.enqueue(object : Callback<List<UserRepositoryResponse>> {
            override fun onResponse(
                call: Call<List<UserRepositoryResponse>>,
                response: Response<List<UserRepositoryResponse>>
            ) {
                loading.value = false
                if (response.isSuccessful) {
                    repository.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserRepositoryResponse>>, t: Throwable) {
                loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getRepository(): LiveData<List<UserRepositoryResponse>> {
        return repository
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }
}