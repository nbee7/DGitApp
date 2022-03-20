package com.submission.dicoding.dgitapp.ui.detail

import androidx.lifecycle.*
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import kotlinx.coroutines.launch

class DetailUserViewModel(private val repo: UserGithubRepository) : ViewModel() {
    private var _getUserDetail = MutableLiveData<Resource<UserDetailResponse>>()
    val getUserdetail: LiveData<Resource<UserDetailResponse>> = _getUserDetail

    fun userDetail(username: String) {
        viewModelScope.launch {
            repo.getUserDetail(username).collect {
                _getUserDetail.value = it
            }
        }
    }

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