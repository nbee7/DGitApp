package com.submission.dicoding.dgitapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.Resource
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import kotlinx.coroutines.launch

class MainViewModel(private val repo: UserGithubRepository) : ViewModel() {
    private val _getUser = MutableLiveData<Resource<List<UserItems>>>()
    val getUser: LiveData<Resource<List<UserItems>>> = _getUser
    private val dummySearch = "brian"

    init {
        searchUser(dummySearch)
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            repo.getSearchUser(username).collect {
                _getUser.value = it
            }
        }
    }
}