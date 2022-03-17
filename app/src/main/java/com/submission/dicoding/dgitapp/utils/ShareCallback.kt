package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserItems

interface ShareCallback {
    fun onShareClick(data: UserItems)
}

interface FavoriteUserShareCallback {
    fun onFavoriteUserShareClick(data: FavoriteUserEntity)
}