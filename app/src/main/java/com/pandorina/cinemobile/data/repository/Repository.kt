package com.pandorina.cinemobile.data.repository

import com.pandorina.cinemobile.data.ApiResponse
import com.pandorina.cinemobile.data.local.LocalDataSource
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.local.model.MovieQuery
import com.pandorina.cinemobile.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource) : ApiResponse() {

    suspend fun getDiscoverMovies() = remoteDataSource.getDiscoverMovies()
    suspend fun getPopularMovies() = remoteDataSource.getPopularMovies()
    suspend fun getTopRatedMovies() = remoteDataSource.getTopRatedMovies()
    suspend fun getNowPlayingMovies() = remoteDataSource.getNowPlayingMovies()
    suspend fun getUpcomingMovies() = remoteDataSource.getUpcomingMovies()

    fun getSearchMovies(query: String) = remoteDataSource.getSearchMovies(query)


    suspend fun insertMovieQuery(query: MovieQuery) = localDataSource.insertMovieQuery(query)
    suspend fun clearMovieQueries() = localDataSource.clearMovieQueries()
    fun getMovieQueries() = localDataSource.getMovieQueries()

    suspend fun insertFavoriteMovie(movie: FavoriteMovie) = localDataSource.insertFavoriteMovie(movie)
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie) = localDataSource.deleteFavoriteMovie(movie)
    fun getFavoriteMovie() = localDataSource.getFavoriteMovies()
    fun checkFavoriteMovieExist(movieId: Int) = localDataSource.checkFavoriteMovieExist(movieId)


    suspend fun getMovieDetail(movieId: Int) = remoteDataSource.getMovieDetail(movieId)
    suspend fun getCast(id: Int, productionType: String) = remoteDataSource.getCast(id, productionType)

    suspend fun getGenres(productionType: String) = remoteDataSource.getGenres(productionType)

    fun getMoviesByGenres(genreId: Int) = remoteDataSource.getMoviesByGenres(genreId)
    fun getMorePopularMovies() = remoteDataSource.getMorePopularMovies()
    fun getMoreTopRatedMovies() = remoteDataSource.getMoreTopRatedMovies()
    fun getMoreNowPlayingMovies() = remoteDataSource.getMoreNowPlayingMovies()
    fun getMoreUpcomingMovies() = remoteDataSource.getMoreUpcomingMovies()


}