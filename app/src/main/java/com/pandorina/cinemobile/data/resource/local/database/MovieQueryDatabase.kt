package com.pandorina.cinemobile.data.resource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pandorina.cinemobile.data.model.MovieQuery
import com.pandorina.cinemobile.data.resource.local.dao.MovieQueryDAO

@Database(entities = [MovieQuery::class], version = 2)
abstract class MovieQueryDatabase : RoomDatabase() {

    abstract fun dao(): MovieQueryDAO
}