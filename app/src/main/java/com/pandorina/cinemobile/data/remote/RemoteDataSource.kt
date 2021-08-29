package com.pandorina.cinemobile.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.pandorina.cinemobile.data.ApiResponse
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.remote.model.GenreResponse
import com.pandorina.cinemobile.data.remote.paging.MoreMoviesPagingSource
import com.pandorina.cinemobile.data.remote.paging.MovieByGenresPagingSource
import com.pandorina.cinemobile.data.remote.paging.SearchMoviesPagingSource
import com.pandorina.cinemobile.data.remote.service.TMDBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import com.pandorina.cinemobile.util.Constant
class RemoteDataSource @Inject constructor(val api: TMDBApi): ApiResponse(){


    suspend fun getDiscoverMovies() = api.getDiscoverMovies(1)

    suspend fun getPopularMovies() = api.getPopularMovies(1)

    suspend fun getTopRatedMovies() = api.getTopRatedMovies(1)

    suspend fun getNowPlayingMovies() = api.getNowPlayingMovies(1)

    suspend fun getUpcomingMovies() = api.getUpcomingMovies(1)

    fun getSearchMovies(query: String) = Pager(PagingConfig(pageSize = 20)){
        SearchMoviesPagingSource(api, query)
    }.liveData

    suspend fun getMovieDetail(movieId: Int) = api.getMovieDetail(movieId)

    suspend fun getCast(id: Int, productionType: String) = api.getCast(productionType, id)

    suspend fun getGenres(productionType: String) =  flow<NetworkResult<GenreResponse>>{
        emit(safeApiCall { api.getGenres(productionType) })
    }.flowOn(Dispatchers.Main)

    fun getMoviesByGenres(genreId: Int) = Pager(PagingConfig(pageSize = 20)){
        MovieByGenresPagingSource(api, genreId) }.flow

    fun getMorePopularMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, Constant.PATH_POPULAR) }.flow

    fun getMoreTopRatedMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, Constant.PATH_TOP_RATED) }.flow

    fun getMoreNowPlayingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, Constant.PATH_NOW_PLAYING) }.flow

    fun getMoreUpcomingMovies() = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, Constant.PATH_UPCOMING) }.flow
}