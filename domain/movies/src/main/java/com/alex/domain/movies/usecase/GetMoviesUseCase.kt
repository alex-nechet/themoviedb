package com.alex.domain.movies.usecase

import com.alex.domain.movies.repository.MovieDbRepository

class GetMoviesUseCase(
    private val moviesRepository: MovieDbRepository
) {
    operator fun invoke(pageSize: Int = DEFAULT_PAGE_SIZE) =
        moviesRepository.getMoviesList(pageSize = pageSize)
}

private const val DEFAULT_PAGE_SIZE = 20