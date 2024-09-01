package com.alex.themoviedb.di

import androidx.paging.PagingSource
import com.alex.data.remote.datasource.MovieDbRemoteDataSourceImpl
import com.alex.data.remote.dto.MovieDto
import com.alex.data.repository.MovieDbRepositoryImpl
import com.alex.domain.movies.repository.MovieDbRepository
import com.alex.domain.movies.usecase.GetMoviesUseCase
import com.alex.themoviedb.BuildConfig
import com.alex.themoviedb.ui.list.MovieListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val remote = module {
    single<PagingSource<Int, MovieDto>> {
        MovieDbRemoteDataSourceImpl(
            authToken = BuildConfig.API_KEY,
            isDebuggable = BuildConfig.DEBUG,
            baseUrl = BuildConfig.BASE_URL
        )
    }
}

val repository = module {
    single<MovieDbRepository> { MovieDbRepositoryImpl(remoteDataSource = get()) }
}

val domain = module {
    factoryOf(::GetMoviesUseCase)
}

val presentation = module {
    viewModelOf(::MovieListViewModel)
}