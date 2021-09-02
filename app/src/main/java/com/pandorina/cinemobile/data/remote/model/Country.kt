package com.pandorina.cinemobile.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(val iso_3166_1: String,
                   val name: String): Parcelable
