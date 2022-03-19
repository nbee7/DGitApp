package com.submission.dicoding.dgitapp.data

import com.submission.dicoding.dgitapp.data.local.LocalDataSource
import com.submission.dicoding.dgitapp.data.local.datastore.AppTheme
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.remote.ApiResponse
import com.submission.dicoding.dgitapp.data.remote.RemoteDataSource
import com.submission.dicoding.dgitapp.data.remote.response.UserDetailResponse
import com.submission.dicoding.dgitapp.data.remote.response.UserItems
import com.submission.dicoding.dgitapp.data.remote.response.UserRepositoryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UserGithubRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appTheme: AppTheme
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
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserDetail(username).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(it.data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Error(it.toString(), null))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(it.message, null))
                    }
                }
            }
        }
    }

    override fun getUserFollowers(username: String): Flow<Resource<List<UserItems>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserFollowers(username).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(it.data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(listOf()))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(it.message, null))
                    }
                }
            }
        }
    }

    override fun getUserFollowings(username: String): Flow<Resource<List<UserItems>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserFollowings(username).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(it.data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(listOf()))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(it.message, null))
                    }
                }
            }
        }
    }

    override fun getUserRepository(username: String): Flow<Resource<List<UserRepositoryResponse>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getUserRepository(username).collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(Resource.Success(it.data))
                    }
                    is ApiResponse.Empty -> {
                        emit(Resource.Success(listOf()))
                    }
                    is ApiResponse.Error -> {
                        emit(Resource.Error(it.message, null))
                    }
                }
            }
        }
    }

    override suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity) {
        localDataSource.inserFavoritetUser(favoriteUser)
    }

    override suspend fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity) {
        localDataSource.deleteFavoriteUser(favoriteUser)
    }

    override fun getUserFavorite(): Flow<List<FavoriteUserEntity>> {
        return localDataSource.getAllFavoriteUser()
    }

    override fun isFavoriteUser(id: String): Flow<Boolean> {
        return localDataSource.isFavoriteUser(id)
    }

    override fun getThemeSetting(): Flow<Boolean> = appTheme.getThemeSetting()

    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        appTheme.saveThemeSetting(isDarkModeActive)
    }
}