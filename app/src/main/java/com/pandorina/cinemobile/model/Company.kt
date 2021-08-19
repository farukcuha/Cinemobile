package com.pandorina.cinemobile.model

import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util


data class Company(val id: Int,
                   val logo_path: String,
                   val name: String,
                   val origin_country: String){
    val logo_path_url get() = Constant.IMAGE_URL_LOW + logo_path
}
