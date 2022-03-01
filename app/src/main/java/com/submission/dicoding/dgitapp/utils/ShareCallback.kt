package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.UserEntity

interface ShareCallback {
    fun onShareClick(data: UserEntity)
}