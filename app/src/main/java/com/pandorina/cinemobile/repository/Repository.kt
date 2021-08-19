package com.pandorina.cinemobile.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandorina.cinemobile.ApiResponse
import com.pandorina.cinemobile.NetworkResult
import com.pandorina.cinemobile.TMDBApi
import com.pandorina.cinemobile.model.GenreResponse
import com.pandorina.cinemobile.paging.MovieByGenresPagingSource
import com.pandorina.cinemobile.paging.MoreMoviesPagingSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(private val api: TMDBApi): ApiResponse() {
    suspend fun getDiscoverMovies() = api.getDiscoverMovies(1)

    suspend fun getPopularMovies() = api.getPopularMovies(1)

    suspend fun getTopRatedMovies() = api.getTopRatedMovies(1)

    suspend fun getNowPlayingMovies() = api.getNowPlayingMovies(1)

    suspend fun getUpcomingMovies() = api.getUpcomingMovies(1)

    suspend fun getMovieDetail(movieId: Int) = api.getMovieDetail(movieId)

    suspend fun getCast(id: Int, productionType: String) = api.getCast(productionType, id)

    fun getGenres(productionType: String) = flow<NetworkResult<GenreResponse>> {
        emit(safeApiCall { api.getGenres(productionType) })
    }.flowOn(Dispatchers.IO)

    fun getMoviesByGenres(genreId: Int) = Pager(PagingConfig(pageSize = 20)){
        MovieByGenresPagingSource(api, genreId) }.flow

    fun getMorePopularMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Popular") }.flow

    fun getMoreTopRatedMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Top Rated") }.flow

    fun getMoreNowPlayingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Now Playing") }.flow

    fun getMoreUpcomingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, "Upcoming") }.flow

}