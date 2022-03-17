package com.submission.dicoding.dgitapp.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userfavorite")
class FavoriteUserEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userId")
    var userId: String,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatar")
    var avatar: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null
)