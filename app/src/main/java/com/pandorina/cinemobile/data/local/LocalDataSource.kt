package com.pandorina.cinemobile.data.local

import com.pandorina.cinemobile.data.local.dao.CinemobileDao
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.local.model.MovieQuery
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: CinemobileDao) {

    suspend fun insertMovieQuery(query: MovieQuery) = dao.insertMovieQuery(query)
    suspend fun clearMovieQueries() = dao.clearMovieQueries()
    fun getMovieQueries() = dao.getMovieQueries()

    suspend fun insertFavoriteMovie(movie: FavoriteMovie) = dao.insertFavoriteMovie(movie)
    suspend fun deleteFavoriteMovie(movie: FavoriteMovie) = dao.deleteFavoriteMovie(movie)
    fun getFavoriteMovies() = dao.getFavoriteMovies()
    fun checkFavoriteMovieExist(movieId: Int) = dao.checkFavoriteMovieExist(movieId)
}