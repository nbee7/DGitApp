package com.submission.dicoding.dgitapp.data.remote

import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUser(
        @Query("q") username: String,
        @Header("Authorization") token: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetail(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserItems>>

    @GET("users/{username}/following")
    fun getFollowings(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserItems>>

    @GET("users/{username}/repos")
    fun getRepository(
        @Path("username") username: String,
        @Header("Authorization") token: String
    ): Call<List<UserRepositoryResponse>>
}