package com.submission.dicoding.dgitapp.data.local

import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import com.submission.dicoding.dgitapp.data.local.room.UserDao

class LocalDataSource(private val mUserDao: UserDao) {

    suspend fun inserFavoritetUser(favoriteUser: FavoriteUserEntity) = mUserDao.insertFavoriteUser(favoriteUser)

    suspend fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity) = mUserDao.deleteFavoriteUser(favoriteUser)

    fun getAllFAvoriteUser() = mUserDao.getAllFavoriteUser()

    fun isFavoriteUser(id: String) = mUserDao.isFavoriteUser(id)
}