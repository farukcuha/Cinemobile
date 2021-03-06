package com.pandorina.cinemobile.data.remote.service

import com.pandorina.cinemobile.data.remote.model.*
import com.pandorina.cinemobile.data.remote.model.Collection
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): Response<MovieResponse>

    @GET("discover/movie")
    suspend fun getDiscoverMovies(
        @Query("with_genres") withGenres: String? = null,
        @Query("page") page: Int,
        @Query("primary_release_date.gte") primaryReleaseYear: String? = null,
        @Query("primary_release_date.lte") seconderReleaseYear: String? = null,
        @Query("with_original_language") withOriginalLanguage: String? = null,
        @Query("vote_average.gte") voteAverageGte: String = "5",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("include_adult") include_adult: Boolean = false,
        @Query("include_video") include_video: Boolean = true,
    ): Response<MovieResponse>

    @GET("movie/{movie_group}")
    suspend fun getMovieGroup(
        @Path("movie_group") movieGroup: String?,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movie_id: Int
    ): Response<MovieDetail>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int?
    ): Response<MovieResponse>

    @GET("collection/{collection_id}")
    suspend fun getMovieCollection(
        @Path("collection_id") collectionId: Int
    ): Response<Collection>

    @GET("{production_type}/{id}/credits")
    suspend fun getCast(
        @Path("production_type") production_type: String,
        @Path("id") id: Int
    ): Response<CreditResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenreResponse>

    @GET("{production_type}/{id}/videos")
    suspend fun getVideos(
        @Path("production_type") type: String,
        @Path("id") movieId: Int?
    ): Response<VideoResponse>
}