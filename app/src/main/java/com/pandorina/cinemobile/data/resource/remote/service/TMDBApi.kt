package com.pandorina.cinemobile.data.resource.remote.service

import androidx.lifecycle.LiveData
import com.pandorina.cinemobile.data.model.CreditResponse
import com.pandorina.cinemobile.data.model.GenreResponse
import com.pandorina.cinemobile.data.model.MovieDetail
import com.pandorina.cinemobile.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {
    @GET("discover/movie")
    suspend fun getDiscoverMovies(
            @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
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

    @GET("{production_type}/{id}/credits")
    suspend fun getCast(
        @Path("production_type") production_type: String,
        @Path("id") id: Int
    ): Response<CreditResponse>

    @GET("genre/{production_type}/list")
    suspend fun getGenres(
            @Path("production_type") production_type: String
    ): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenres(
        @Query("with_genres") with_genres: Int,
        @Query("page") page: Int
    ): Response<MovieResponse>

    /*@GET("/movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movie_id: Int,
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("/movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movie_id: Int
    ): Response<List<CreditResponse>>

    @GET("/person/{person_id}")
    fun getCreditDetails(
        @Path("person_id") person_id: Int
    ): Response<List<Credit>>

    @GET("/search/movie")
    fun getSearchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("/tv/popular")
    fun getPopularTvShows(
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("/tv/top_rated")
    fun getTopRatedTvShows(
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("/tv/airing_today")
    fun getAiringTodayTvShows(
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("/tv/on_the_air")
    fun getOnTheAirTvShows(
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>

    @GET("search/tv")
    fun getSearchTvShows(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<List<ProductionResponse>>*/


}