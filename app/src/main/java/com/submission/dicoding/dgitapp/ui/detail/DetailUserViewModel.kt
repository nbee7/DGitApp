package com.submission.dicoding.dgitapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import kotlinx.coroutines.launch

class DetailUserViewModel(private val repo: UserGithubRepository): ViewModel() {
    fun userDetail(username: String): LiveData<Resource<UserDetailResponse>> =
        repo.getUserDetail(username).asLiveData()

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

    fun isFavoriteUser(id: String) = repo.isFavoriteUser(id).asLiveData()
}