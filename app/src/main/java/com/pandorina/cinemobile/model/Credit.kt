package com.pandorina.cinemobile.model

import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util

data class Credit(val id: Int,
                  val known_for_department: String,
                  val name: String,
                  val original_name: String,
                  val profile_path: String,
                  val cast_id: Int,
                  val character: String,
                  val credit_id: String){
    val profile_path_url get() = Constant.IMAGE_URL_LOW + profile_path
}
