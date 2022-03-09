package com.submission.dicoding.dgitapp.ui.detail.userrepo

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
    private val _repository = MutableLiveData<List<UserRepositoryResponse>>(null)
    val repository: LiveData<List<UserRepositoryResponse>> = _repository
    private val _loading = MutableLiveData(true)
    val loading: LiveData<Boolean> = _loading
    private val token = "token " + BuildConfig.API_KEY

    fun userRepository(username: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().getRepository(username, token)
        client.enqueue(object : Callback<List<UserRepositoryResponse>> {
            override fun onResponse(
                call: Call<List<UserRepositoryResponse>>,
                response: Response<List<UserRepositoryResponse>>
            ) {
                _loading.value = false
                if (response.isSuccessful) {
                    _repository.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserRepositoryResponse>>, t: Throwable) {
                _loading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}