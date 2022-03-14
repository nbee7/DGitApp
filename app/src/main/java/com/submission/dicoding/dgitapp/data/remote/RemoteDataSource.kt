package com.submission.dicoding.dgitapp.data.remote

import android.util.Log
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.network.ApiService
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception

class RemoteDataSource(private val apiService: ApiService) {
    private val token = "token " + BuildConfig.API_KEY

    suspend fun getSearcUser(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.searchUser(username, token)
                val dataUserItems = response.items

                if (dataUserItems.isNotEmpty()) {
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserDetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(token, username)
                if (response.name.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.getFollowers(token, username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowings(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.getFollowers(token, username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserRepository(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.getFollowers(token, username)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}