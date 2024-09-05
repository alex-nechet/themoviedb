package com.alex.domain.movies.usecase

import com.alex.domain.movies.repository.MovieDbRepository

class SearchMoviesUseCase(
    private val moviesRepository: MovieDbRepository
) {
    operator fun invoke(query: String, pageSize: Int = DEFAULT_PAGE_SIZE) =
        moviesRepository.getMovieSuggestions(
            query = query,
            pageSize = pageSize
        )
}

private const val DEFAULT_PAGE_SIZE = 20