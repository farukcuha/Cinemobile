package com.pandorina.cinemobile.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "favorite_movie_table", primaryKeys = ["movie_id"])
data class FavoriteMovie(
    @ColumnInfo(name = "movie_id") val movieId: Int,
    @ColumnInfo(name = "time_stamp") val timeStamp: Long = System.currentTimeMillis()
)
