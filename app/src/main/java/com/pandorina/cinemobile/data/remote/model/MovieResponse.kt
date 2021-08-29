package com.pandorina.cinemobile.data.remote.model

data class MovieResponse(val page: Int,
                         val results: ArrayList<Movie>,
                         val total_results: Int,
                         val total_pages: Int)
