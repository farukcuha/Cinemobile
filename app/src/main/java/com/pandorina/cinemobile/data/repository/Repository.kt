package com.pandorina.cinemobile.data.repository

import com.pandorina.cinemobile.data.ApiResponse
import com.pandorina.cinemobile.data.local.LocalDataSource
import com.pandorina.cinemobile.data.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource
) : ApiResponse() {
}