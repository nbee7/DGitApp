package com.submission.dicoding.dgitapp.data

import com.submission.dicoding.dgitapp.data.local.LocalDataSource
import com.submission.dicoding.dgitapp.data.remote.ApiResponse
import com.submission.dicoding.dgitapp.data.remote.RemoteDataSource
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserGithubRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
): DataSource{
    override fun getSearchUser(username: String): Flow<Resource<List<UserItems>>> {
        return flow {
            emit(Resource.Loading())
            when (val response = remoteDataSource.getSearcUser(username).first()) {
                is ApiResponse.Success -> {
                    val result = response.data
                    emit(Resource.Success(result))
                }
                is ApiResponse.Empty -> {
                    emit(Resource.Success(listOf()))
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.message, null))
                }
            }
        }
    }

    override fun getUserDetail(username: String): Flow<Resource<UserDetailResponse>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowers(username: String): Flow<Resource<List<UserItems>>> {
        TODO("Not yet implemented")
    }

    override fun getUserFollowings(username: String): Flow<Resource<List<UserItems>>> {
        TODO("Not yet implemented")
    }

    override fun getUserRepository(username: String): Flow<Resource<List<UserRepositoryResponse>>> {
        TODO("Not yet implemented")
    }
}