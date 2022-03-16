package com.submission.dicoding.dgitapp.ui.detail.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import kotlinx.coroutines.launch

class FollowViewModel(private val repo: UserGithubRepository): ViewModel() {
    private val _getUserFollowers = MutableLiveData<Resource<List<UserItems>>>()
    val getUserFollowers: LiveData<Resource<List<UserItems>>> = _getUserFollowers
    private val _getUserFollowings = MutableLiveData<Resource<List<UserItems>>>()
    val getUserFollowings: LiveData<Resource<List<UserItems>>> = _getUserFollowings

    fun userFollowers(username: String) {
        viewModelScope.launch {
            repo.getUserFollowers(username).collect {
                _getUserFollowers.value = it
            }
        }
    }

    fun userFollowings(username: String) {
        viewModelScope.launch {
            repo.getUserFollowings(username).collect {
                _getUserFollowings.value = it
            }
        }
    }
}