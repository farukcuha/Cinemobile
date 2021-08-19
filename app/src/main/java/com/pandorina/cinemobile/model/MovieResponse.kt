package com.pandorina.cinemobile.model

data class MovieResponse(val page: Int,
                         val results: ArrayList<Movie>,
                         val total_results: Int,
                         val total_pages: Int)
