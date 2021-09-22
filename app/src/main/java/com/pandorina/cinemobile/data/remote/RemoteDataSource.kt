package com.pandorina.cinemobile.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.pandorina.cinemobile.data.ApiResponse
import com.pandorina.cinemobile.data.NetworkResult
import com.pandorina.cinemobile.data.remote.paging.MoreMoviesPagingSource
import com.pandorina.cinemobile.data.remote.paging.MovieByGenresPagingSource
import com.pandorina.cinemobile.data.remote.paging.SearchMoviesPagingSource
import com.pandorina.cinemobile.data.remote.service.MovieService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val api: MovieService) : ApiResponse() {
    suspend fun getDiscoverMovies() = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getDiscoverMovies(null, 1) })
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieGroup(movieGroup: String, page: Int) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getMovieGroup(movieGroup, page) })
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieDetail(movieId: Int) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getMovieDetail(movieId) })
    }.flowOn(Dispatchers.IO)

    suspend fun getMovieCollection(collectionId: Int) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getMovieCollection(collectionId) })
    }.flowOn(Dispatchers.IO)

    suspend fun getSimilarMovies(movieId: Int?) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getSimilarMovies(movieId) })
    }.flowOn(Dispatchers.IO)

    fun getSearchMovies(query: String) = Pager(PagingConfig(pageSize = 20)) {
        SearchMoviesPagingSource(api, query)
    }.liveData

    fun getMoviesByGenres(genreId: Int) = Pager(PagingConfig(pageSize = 20)) {
        MovieByGenresPagingSource(api, genreId)
    }.flow

    fun getMoreMovieGroup(path: String) = Pager(PagingConfig(pageSize = 20)) {
        MoreMoviesPagingSource(api, path)
    }.flow

    suspend fun getVideos(path: String, id: Int) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getVideos(path, id) })
    }.flowOn(Dispatchers.IO)

    suspend fun getGenres(productionType: String) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getGenres(productionType) })
    }.flowOn(Dispatchers.IO)

    suspend fun getCast(id: Int, productionType: String) = flow {
        emit(NetworkResult.Loading())
        emit(safeApiCall { api.getCast(productionType, id) })
    }.flowOn(Dispatchers.IO)
}