package com.submission.dicoding.dgitapp.data.local.room

import androidx.room.Database
import com.submission.dicoding.dgitapp.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase {
    abstract fun userDao(): UserDao
}