package com.alex.themoviedb.di

import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.datasource.MovieDbRemoteDataSourceImpl
import com.alex.data.repository.MovieDbRepositoryImpl
import com.alex.domain.movies.repository.MovieDbRepository
import com.alex.domain.movies.usecase.GetMovieDetailsUseCase
import com.alex.domain.movies.usecase.GetMoviesUseCase
import com.alex.themoviedb.BuildConfig
import com.alex.themoviedb.ui.details.MovieDetailsViewModel
import com.alex.themoviedb.ui.list.MovieListViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remote = module {
    single {
        MovieDbRemoteDataSourceImpl(
            authToken = BuildConfig.API_KEY,
            isDebuggable = BuildConfig.DEBUG,
            baseUrl = BuildConfig.BASE_URL
        )
    } bind MovieDbRemoteDataSource::class
}

val repository = module {
    single { MovieDbRepositoryImpl(remoteDataSource = get()) } bind MovieDbRepository::class
}

val domain = module {
    factoryOf(::GetMoviesUseCase)
    factoryOf(::GetMovieDetailsUseCase)
}

val presentation = module {
    viewModelOf(::MovieListViewModel)
    viewModel { (movieId: Long) -> MovieDetailsViewModel(movieId) }
}