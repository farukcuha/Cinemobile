package com.pandorina.cinemobile.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pandorina.cinemobile.data.model.GenreResponse
import com.pandorina.cinemobile.data.resource.paging.MoreMoviesPagingSource
import com.pandorina.cinemobile.data.resource.paging.MovieByGenresPagingSource
import com.pandorina.cinemobile.data.resource.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
    ): ApiResponse() {
    suspend fun getDiscoverMovies() = remoteDataSource.getDiscoverMovies()

    suspend fun getPopularMovies() = remoteDataSource.getPopularMovies()

    suspend fun getTopRatedMovies() = remoteDataSource.getTopRatedMovies()

    suspend fun getNowPlayingMovies() = remoteDataSource.getNowPlayingMovies()

    suspend fun getUpcomingMovies() = remoteDataSource.getUpcomingMovies()

    suspend fun getMovieDetail(movieId: Int) = remoteDataSource.getMovieDetail(movieId)

    suspend fun getCast(id: Int, productionType: String) = remoteDataSource.getCast(id, productionType)

    suspend fun getGenres(productionType: String) = flow<NetworkResult<GenreResponse>>{
        emit(safeApiCall { remoteDataSource.getGenres(productionType) })
    }.flowOn(Dispatchers.Main)

    fun getMoviesByGenres(genreId: Int) = remoteDataSource.getMoviesByGenres(genreId).flow

    fun getMorePopularMovies() = remoteDataSource.getMorePopularMovies().flow

    fun getMoreTopRatedMovies() = remoteDataSource.getMoreTopRatedMovies().flow

    fun getMoreNowPlayingMovies() = remoteDataSource.getMoreNowPlayingMovies().flow

    fun getMoreUpcomingMovies() = remoteDataSource.getMoreUpcomingMovies().flow
}