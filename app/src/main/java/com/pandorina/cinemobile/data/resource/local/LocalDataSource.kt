package com.pandorina.cinemobile.data.resource.local

import android.content.Context
import com.pandorina.cinemobile.data.model.MovieQuery
import com.pandorina.cinemobile.data.resource.local.dao.MovieQueryDAO
import com.pandorina.cinemobile.data.resource.local.database.MovieQueryDatabase
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: MovieQueryDAO) {

    suspend fun insertMovieQuery(query: MovieQuery) = dao.insertMovieQuery(query)

    suspend fun clearMovieQueries() = dao.clearMovieQueries()

    fun getMovieQueries() = dao.getMovieQueries()
}