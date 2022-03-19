package com.submission.dicoding.dgitapp.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.submission.dicoding.dgitapp.data.UserGithubRepository
import kotlinx.coroutines.launch

class SettingViewModel(private val repo: UserGithubRepository) : ViewModel() {

    fun getThemeSetting() = repo.getThemeSetting().asLiveData()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            repo.saveThemeSetting(isDarkModeActive)
        }
    }
}