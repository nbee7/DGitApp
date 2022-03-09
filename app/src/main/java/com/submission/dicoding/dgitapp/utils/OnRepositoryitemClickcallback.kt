package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse

interface OnRepositoryitemClickcallback {
    fun onItemRepositoryClicked(data: UserRepositoryResponse)
}