package com.pandorina.cinemobile.model

import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util

data class Collection(val id: String,
                      val name: String,
                      val poster_path: String,
                      val backdrop_path: String) {

    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
