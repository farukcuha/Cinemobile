package com.pandorina.cinemobile.data.remote.model

import android.os.Parcelable
import androidx.room.Entity
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(val poster_path: String?,
                 val overview: String?,
                 val release_date: String?,
                 val genre_ids: List<Int>?,
                 val id: Int?,
                 val originalTitle: String?,
                 val original_language: String?,
                 val title: String?,
                 val backdrop_path: String?): Parcelable{
    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
