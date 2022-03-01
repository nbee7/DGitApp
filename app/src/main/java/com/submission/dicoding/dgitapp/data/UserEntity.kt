package com.submission.dicoding.dgitapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val follower: Int? = null,
    val following: Int? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val avatar: String? = null,
    val repository: Int? = null,
    val username: String? = null
) : Parcelable