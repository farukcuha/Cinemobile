package com.pandorina.cinemobile.data.local.model

import androidx.room.Entity
import com.pandorina.cinemobile.util.Constant

@Entity(tableName = "favorite_movie_table", primaryKeys = ["movie_id"])
data class FavoriteMovie(
        val movie_id: Int?,
        val poster_path: String?,
        val title: String?,
        val overview: String?,
        val release_date: String?,
        val backdrop_path: String?,
        val time_stamp: Long = System.currentTimeMillis()
){
    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
