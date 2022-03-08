package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.remote.response.UserItems

interface ShareCallback {
    fun onShareClick(data: UserItems)
}