package com.submission.dicoding.dgitapp.data

import com.submission.dicoding.dgitapp.data.remote.ApiResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getSearchUser(username: String): Flow<Resource<List<UserItems>>>

    fun getUserDetail(username: String): Flow<Resource<UserDetailResponse>>

    fun getUserFollowers(username: String): Flow<Resource<List<UserItems>>>

    fun getUserFollowings(username: String): Flow<Resource<List<UserItems>>>

    fun getUserRepository(username: String): Flow<Resource<List<UserRepositoryResponse>>>
}