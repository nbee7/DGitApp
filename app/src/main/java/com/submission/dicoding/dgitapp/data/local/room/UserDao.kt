package com.submission.dicoding.dgitapp.data.local.room

import androidx.room.*
import com.submission.dicoding.dgitapp.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM userfavorite WHERE isUserFavorite = 1")
    fun getAllFavoriteUser()
}