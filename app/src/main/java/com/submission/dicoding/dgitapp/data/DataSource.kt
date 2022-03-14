package com.submission.dicoding.dgitapp.data

import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import kotlinx.coroutines.flow.Flow

interface DataSource {

    fun getSearchUser(username: String): Flow<Resource<List<UserItems>>>

    fun getUserDetail(username: String): Flow<Resource<UserDetailResponse>>

    fun getUserFollowers(username: String): Flow<Resource<List<UserItems>>>

    fun getUserFollowings(username: String): Flow<Resource<List<UserItems>>>

    fun getUserRepository(username: String): Flow<Resource<List<UserRepositoryResponse>>>

    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    suspend fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity)

    fun getUserFavorite(): Flow<List<FavoriteUserEntity>>

    fun isFavoriteUser(id: String): Flow<Boolean>
}