package com.pandorina.cinemobile.data.remote.model

class SeriesResponse(val page: Int,
                     val results: ArrayList<Series>,
                     val total_results: Int,
                     val total_pages: Int) {
}