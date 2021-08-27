package com.pandorina.cinemobile.data.resource.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pandorina.cinemobile.data.model.MovieQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieQueryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieQuery(vararg query: MovieQuery)

    @Query("DELETE FROM MOVIE_SEARCH_QUERY_TABLE")
    suspend fun clearMovieQueries()

    @Query("SELECT * FROM MOVIE_SEARCH_QUERY_TABLE ORDER BY query_time DESC")
    fun getMovieQueries(): Flow<List<MovieQuery>>

}