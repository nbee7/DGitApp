package com.submission.dicoding.dgitapp.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.submission.dicoding.dgitapp.data.local.entity.FavoriteUserEntity

@Database(entities = [FavoriteUserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}