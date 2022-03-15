package com.submission.dicoding.dgitapp.ui.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.network.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repo: UserGithubRepository): ViewModel() {

}