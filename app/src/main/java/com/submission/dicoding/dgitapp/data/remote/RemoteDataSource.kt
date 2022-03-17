package com.submission.dicoding.dgitapp.data.remote

import android.util.Log
import com.submission.dicoding.dgitapp.BuildConfig
import com.submission.dicoding.dgitapp.data.remote.network.ApiService
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

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
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(username: String): Flow<ApiResponse<UserDetailResponse>> {
        return flow {
            try {
                val response = apiService.getDetail(username, token)
                if (response.name.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowers(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username, token)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserFollowings(username: String): Flow<ApiResponse<List<UserItems>>> {
        return flow {
            try {
                val response = apiService.getFollowings(username, token)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserRepository(username: String): Flow<ApiResponse<List<UserRepositoryResponse>>> {
        return flow {
            try {
                val response = apiService.getRepository(username, token)
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.message.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}