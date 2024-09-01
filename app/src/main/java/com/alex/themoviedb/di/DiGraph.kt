package com.alex.themoviedb.di

import androidx.paging.PagingSource
import com.alex.data.remote.datasource.MovieDbRemoteDataSourceImpl
import com.alex.data.remote.dto.MovieDto
import com.alex.data.repository.MovieDbRepositoryImpl
import com.alex.domain.movies.repository.MovieDbRepository
import com.alex.themoviedb.BuildConfig
import org.koin.dsl.module

val remote = module {
    single<PagingSource<Int, MovieDto>> {
        MovieDbRemoteDataSourceImpl(
            authToken = BuildConfig.API_KEY,
            isDebuggable = BuildConfig.DEBUG
        )
    }
}

val repository = module {
    single<MovieDbRepository> { MovieDbRepositoryImpl(remoteDataSource = get()) }
}

val domain = module {  }