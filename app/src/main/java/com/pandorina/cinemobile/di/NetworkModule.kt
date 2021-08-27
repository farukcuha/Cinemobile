package com.pandorina.cinemobile.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.pandorina.cinemobile.R
import com.pandorina.cinemobile.data.resource.local.LocalDataSource
import com.pandorina.cinemobile.data.resource.local.dao.MovieQueryDAO
import com.pandorina.cinemobile.data.resource.local.database.MovieQueryDatabase
import com.pandorina.cinemobile.data.resource.remote.RemoteDataSource
import com.pandorina.cinemobile.data.resource.remote.service.TMDBApi
import com.pandorina.cinemobile.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
                .baseUrl(Constant.API_URL)
                .client(OkHttpClient.Builder()
                        .addInterceptor { chain ->
                            val url = chain
                                    .request()
                                    .url()
                                    .newBuilder()
                                    .addQueryParameter("api_key", Constant.API_KEY)
                                    .addQueryParameter("language", context.getString(R.string.language))
                                    .build()
                            chain.proceed(chain.request().newBuilder().url(url).build())
                        }
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.MINUTES)
                        .writeTimeout(30, TimeUnit.MINUTES)
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): TMDBApi = retrofit.create(TMDBApi::class.java)

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: TMDBApi): RemoteDataSource = RemoteDataSource(api)


}