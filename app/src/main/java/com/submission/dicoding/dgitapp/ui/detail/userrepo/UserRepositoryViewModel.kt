package com.submission.dicoding.dgitapp.ui.detail.userrepo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicoding.dgitapp.data.UserGithubRepository

class UserRepositoryViewModel(private val repo: UserGithubRepository): ViewModel() {
    fun userRepository(username: String) = repo.getUserRepository(username).asLiveData()
}