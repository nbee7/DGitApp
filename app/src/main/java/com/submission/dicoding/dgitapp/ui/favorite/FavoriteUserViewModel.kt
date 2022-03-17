package com.submission.dicoding.dgitapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.submission.dicoding.dgitapp.data.UserGithubRepository

class FavoriteUserViewModel(private val repo: UserGithubRepository): ViewModel() {
    fun getAllFavoriteUser() = repo.getUserFavorite().asLiveData()
}