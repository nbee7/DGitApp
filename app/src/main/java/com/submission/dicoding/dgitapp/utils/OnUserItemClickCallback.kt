package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserItems

interface OnUserItemClickCallback {
    fun onUserItemClicked(data: UserItems)
}

interface OnFavoriteUserItemClickCallback {
    fun onFavoriteUserItemClicked(data: FavoriteUserEntity)
}