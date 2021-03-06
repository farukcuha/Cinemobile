package com.pandorina.cinemobile.data.remote.model

import android.os.Parcelable
import com.pandorina.cinemobile.util.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieDetail(val backdrop_path: String?,
                       val belongs_to_collection: Collection?,
                       val budget: Long?,
                       val genres: List<Genre>?,
                       val homepage: String?,
                       val id: Int?,
                       val imdb_id: String?,
                       val original_language: String?,
                       val original_title: String?,
                       val overview: String?,
                       val poster_path: String?,
                       val production_companies: List<Company>?,
                       val production_countries: List<Country>?,
                       val release_date: String?,
                       val revenue: Long?,
                       val runtime: Int?,
                       val tagline: String?,
                       val title: String?): Parcelable{
    val posterPathUrl: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"
}
