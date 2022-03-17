package com.submission.dicoding.dgitapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse

class DetailUserViewModel(private val repo: UserGithubRepository): ViewModel() {
    fun userDetail(username: String): LiveData<Resource<UserDetailResponse>> =
        repo.getUserDetail(username).asLiveData()
}