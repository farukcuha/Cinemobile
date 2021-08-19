package com.pandorina.cinemobile.model

import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util

data class Series(val poster_path: String,
                  val id: String,
                  val backdrop_path: String,
                  val overview: String,
                  val first_air_date: String,
                  val genre_ids: List<Int>,
                  val original_language: String,
                  val name: String,
                  val original_name: String){
    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
