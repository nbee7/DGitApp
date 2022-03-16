package com.submission.dicoding.dgitapp.ui.detail.userrepo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import kotlinx.coroutines.launch

class UserRepositoryViewModel(private val repo: UserGithubRepository): ViewModel() {
    private val _getUserRepository = MutableLiveData<Resource<List<UserRepositoryResponse>>>()
    val getUserRepository: LiveData<Resource<List<UserRepositoryResponse>>> = _getUserRepository

    fun userRepository(username: String) {
        viewModelScope.launch {
            repo.getUserRepository(username).collect {
                _getUserRepository.value = it
            }
        }
    }
}