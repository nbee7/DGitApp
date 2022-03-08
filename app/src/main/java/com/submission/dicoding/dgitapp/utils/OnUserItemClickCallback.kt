package com.submission.dicoding.dgitapp.utils

import com.submission.dicoding.dgitapp.data.remote.response.UserItems

interface OnUserItemClickCallback {
    fun onUserItemClicked(data: UserItems)
}