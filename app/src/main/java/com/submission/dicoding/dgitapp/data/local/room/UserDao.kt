package com.submission.dicoding.dgitapp.data.local.room

import androidx.room.*
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Delete
    suspend fun deleteFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM userfavorite")
    fun getAllFavoriteUser(): Flow<List<FavoriteUserEntity>>

    @Query("SELECT EXISTS(SELECT * FROM userfavorite WHERE userId = :userId)")
    fun isFavoriteUser(userId: String): Flow<Boolean>
}