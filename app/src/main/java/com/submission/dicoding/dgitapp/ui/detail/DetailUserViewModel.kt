package com.submission.dicoding.dgitapp.ui.detail

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.network.ApiConfig
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(private val repo: UserGithubRepository): ViewModel() {

}