package com.pandorina.cinemobile.data.remote.model

import android.os.Parcelable
import com.pandorina.cinemobile.util.Constant
import kotlinx.parcelize.Parcelize

@Parcelize
data class Collection(val id: Int?,
                      val name: String?,
                      val poster_path: String?,
                      val backdrop_path: String?,
                      val parts: List<Movie>?): Parcelable {

    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
