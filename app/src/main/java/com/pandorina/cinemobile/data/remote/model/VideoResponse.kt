package com.pandorina.cinemobile.data.remote.model

data class VideoResponse(
    val id: Int?,
    val results: ArrayList<Video>?
) {
    data class Video(
        val id: String?,
        val iso_3166_1: String?,
        val iso_639_1: String?,
        val key: String?,
        val name: String?,
        val official: Boolean?,
        val published_at: String?,
        val site: String?,
        val size: Int?,
        val type: String?
    ) {
        val thumbnailUrl: String
            get() = "http://i3.ytimg.com/vi/${key}/hqdefault.jpg"

        val videoUrl: String
            get() = "http://www.youtube.com/watch?v=$key"
    }
}
