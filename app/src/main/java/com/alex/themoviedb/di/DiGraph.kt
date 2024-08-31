package com.alex.themoviedb.di

import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.datasource.MovieDbRemoteDataSourceImpl
import com.alex.themoviedb.BuildConfig
import org.koin.dsl.module

val remote = module {
    single<MovieDbRemoteDataSource> {
        MovieDbRemoteDataSourceImpl(
            authToken = BuildConfig.API_KEY,
            isDebuggable = BuildConfig.DEBUG
        )
    }
}