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

    fun getSearchMovies(query: String) = remoteDataSource.getSearchMovies(query)

    suspend fun getMovieDetail(movieId: Int) = remoteDataSource.getMovieDetail(movieId)

    suspend fun getCast(id: Int, productionType: String) = remoteDataSource.getCast(id, productionType)

    suspend fun getGenres(productionType: String) = remoteDataSource.getGenres(productionType)

    fun getMoviesByGenres(genreId: Int) = remoteDataSource.getMoviesByGenres(genreId)

    fun getMorePopularMovies() = remoteDataSource.getMorePopularMovies()

    fun getMoreTopRatedMovies() = remoteDataSource.getMoreTopRatedMovies()

    fun getMoreNowPlayingMovies() = remoteDataSource.getMoreNowPlayingMovies()

    fun getMoreUpcomingMovies() = remoteDataSource.getMoreUpcomingMovies()


}