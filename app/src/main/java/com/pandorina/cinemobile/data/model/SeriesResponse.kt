package com.pandorina.cinemobile.data.model

class SeriesResponse(val page: Int,
                     val results: ArrayList<Series>,
                     val total_results: Int,
                     val total_pages: Int) {
}