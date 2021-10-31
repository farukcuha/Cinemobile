package com.pandorina.cinemobile.data.remote.model

import android.os.Parcelable
import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util
import kotlinx.parcelize.Parcelize

@Parcelize
data class Company(val id: Int,
                   val logo_path: String,
                   val name: String,
                   val origin_country: String): Parcelable{
    val logoPathUr get() = Constant.IMAGE_URL_LOW + logo_path
}
