package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.UserEntity

interface OnUserItemClickCallback {
    fun onUserItemClicked(data: UserEntity)
}