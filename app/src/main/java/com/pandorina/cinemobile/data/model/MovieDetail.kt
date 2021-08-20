package com.pandorina.cinemobile.data.model

import com.pandorina.cinemobile.util.Constant
import com.pandorina.cinemobile.util.Util

data class MovieDetail(val backdrop_path: String,
                       val belongs_to_collection: Any,
                       val budget: Long,
                       val genres: List<Genre>,
                       val homepage: String,
                       val id: Int,
                       val imdb_id: String,
                       val original_language: String,
                       val original_title: String,
                       val overview: String,
                       val poster_path: String,
                       val production_companies: List<Company>,
                       val production_countries: List<Country>,
                       val release_date: String,
                       val revenue: Long,
                       val runtime: Int,
                       val spoken_language: List<Language>,
                       val tagline: String,
                       val title: String){
    val poster_path_url: String
        get() = "${Constant.IMAGE_URL_LOW}$poster_path"

    val backdrop_path_url: String
        get() = "${Constant.IMAGE_URL_HIGH}$backdrop_path"
}
