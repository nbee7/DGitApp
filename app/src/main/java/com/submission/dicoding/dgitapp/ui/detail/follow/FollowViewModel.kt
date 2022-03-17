package com.submission.dicoding.dgitapp.ui.detail.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicoding.dgitapp.data.UserGithubRepository

class FollowViewModel(private val repo: UserGithubRepository): ViewModel() {

    fun userFollowers(username: String) = repo.getUserFollowers(username).asLiveData()

    fun userFollowings(username: String) = repo.getUserFollowings(username).asLiveData()
}