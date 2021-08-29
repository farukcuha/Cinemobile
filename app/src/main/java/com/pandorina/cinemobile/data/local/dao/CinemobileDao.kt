package com.pandorina.cinemobile.data.local.dao

import androidx.room.*
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.local.model.MovieQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface CinemobileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieQuery(vararg query: MovieQuery)

    @Query("DELETE FROM MOVIE_SEARCH_QUERY_TABLE")
    suspend fun clearMovieQueries()

    @Query("SELECT * FROM MOVIE_SEARCH_QUERY_TABLE ORDER BY query_time DESC")
    fun getMovieQueries(): Flow<List<MovieQuery>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(vararg movie: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(vararg movie: FavoriteMovie)

    @Query("SELECT * FROM FAVORITE_MOVIE_TABLE ORDER BY time_stamp DESC")
    fun getFavoriteMovies(): Flow<List<FavoriteMovie>>

    @Query("SELECT EXISTS(SELECT * FROM FAVORITE_MOVIE_TABLE WHERE movie_id = :movieId)")
    fun checkFavoriteMovieExist(movieId: Int): Flow<Boolean>
}