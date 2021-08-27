package com.pandorina.cinemobile.di

import android.app.Application
import androidx.room.Room
import com.pandorina.cinemobile.ApplicationComponent
import com.pandorina.cinemobile.data.resource.local.LocalDataSource
import com.pandorina.cinemobile.data.resource.local.dao.MovieQueryDAO
import com.pandorina.cinemobile.data.resource.local.database.MovieQueryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideMovieQueryDatabase(app: Application
    ) = Room.databaseBuilder(app,
        MovieQueryDatabase::class.java,
        "movie_search_query_table.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideMovieQueryDao(database: MovieQueryDatabase) = database.dao()

    @Provides
    fun provideLocalDataSource(dao: MovieQueryDAO) = LocalDataSource(dao)
}