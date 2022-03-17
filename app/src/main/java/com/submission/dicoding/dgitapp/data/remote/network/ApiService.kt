package com.submission.dicoding.dgitapp.data.remote.network

import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String,
        @Header("Authorization") token: String
    ): UserResponse

    @GET("users/{username}")
    suspend fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): UserDetailResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<UserItems>

    @GET("users/{username}/following")
    suspend fun getFollowings(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<UserItems>

    @GET("users/{username}/repos")
    suspend fun getRepository(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): List<UserRepositoryResponse>
}