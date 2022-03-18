package com.submission.dicoding.dgitapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.launch

class FavoriteUserViewModel(private val repo: UserGithubRepository): ViewModel() {
    fun getAllFavoriteUser() = repo.getUserFavorite().asLiveData()

    fun saveToFavorite(user: FavoriteUserEntity) {
        viewModelScope.launch {
            repo.insertFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(user: FavoriteUserEntity) {
        viewModelScope.launch {
            repo.deleteFavoriteUser(user)
        }
    }
}