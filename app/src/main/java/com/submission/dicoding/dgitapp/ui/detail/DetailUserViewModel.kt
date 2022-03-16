package com.submission.dicoding.dgitapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import kotlinx.coroutines.launch

class DetailUserViewModel(private val repo: UserGithubRepository): ViewModel() {
    private val _getUserDetail = MutableLiveData<Resource<UserDetailResponse>>()
    val getUserDetail: LiveData<Resource<UserDetailResponse>> = _getUserDetail

    fun userDetail(username: String) {
        viewModelScope.launch {
            repo.getUserDetail(username).collect {
                _getUserDetail.value = it
            }
        }
    }
}