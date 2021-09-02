package com.pandorina.cinemobile.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pandorina.cinemobile.data.local.dao.CinemobileDao
import com.pandorina.cinemobile.data.local.model.FavoriteMovie
import com.pandorina.cinemobile.data.local.model.MovieQuery

@Database(entities = [MovieQuery::class, FavoriteMovie::class], version = 2, exportSchema = false)
abstract class MovieQueryDatabase : RoomDatabase() {

    abstract fun dao(): CinemobileDao
}