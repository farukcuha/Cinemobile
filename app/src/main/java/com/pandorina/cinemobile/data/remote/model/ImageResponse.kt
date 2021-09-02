package com.pandorina.cinemobile.data.remote.model

data class ImageResponse(
        val backdropImages: List<BackdropImage>?,
        val id: Int?,
        val logoImages: List<LogoImage>?,
        val posterImages: List<PosterImage>?
){
    data class BackdropImage(
            val aspect_ratio: Double?,
            val file_path: String?,
            val height: Int?,
            val iso_639_1: String?,
            val vote_average: Double?,
            val vote_count: Int?,
            val width: Int?)

    data class LogoImage(
            val aspect_ratio: Double?,
            val file_path: String?,
            val height: Int?,
            val iso_639_1: String?,
            val vote_average: Int?,
            val vote_count: Int?,
            val width: Int)

    data class PosterImage(
            val aspect_ratio: Double?,
            val file_path: String?,
            val height: Int?,
            val iso_639_1: String?,
            val vote_average: Double?,
            val vote_count: Int?,
            val width: Int?)
}