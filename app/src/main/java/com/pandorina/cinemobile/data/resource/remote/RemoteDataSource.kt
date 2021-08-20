package com.pandorina.cinemobile.data.resource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandorina.cinemobile.data.resource.paging.MoreMoviesPagingSource
import com.pandorina.cinemobile.data.resource.paging.MovieByGenresPagingSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val api: TMDBApi){
    suspend fun getDiscoverMovies() = api.getDiscoverMovies(1)

    suspend fun getPopularMovies() = api.getPopularMovies(1)

    suspend fun getTopRatedMovies() = api.getTopRatedMovies(1)

    suspend fun getNowPlayingMovies() = api.getNowPlayingMovies(1)

    suspend fun getUpcomingMovies() = api.getUpcomingMovies(1)

    suspend fun getMovieDetail(movieId: Int) = api.getMovieDetail(movieId)

    suspend fun getCast(id: Int, productionType: String) = api.getCast(productionType, id)

    suspend fun getGenres(productionType: String) = api.getGenres(productionType)

    fun getMoviesByGenres(genreId: Int) = Pager(PagingConfig(pageSize = 20)){
        MovieByGenresPagingSource(api, genreId) }

    fun getMorePopularMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Popular") }

    fun getMoreTopRatedMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Top Rated") }

    fun getMoreNowPlayingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Now Playing") }

    fun getMoreUpcomingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Upcoming") }
}