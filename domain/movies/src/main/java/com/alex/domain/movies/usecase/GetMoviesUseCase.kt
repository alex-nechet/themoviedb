package com.alex.domain.movies.usecase

import com.alex.domain.movies.entity.Sorting
import com.alex.domain.movies.repository.MovieDbRepository

class GetMoviesUseCase(
    private val moviesRepository: MovieDbRepository
) {
    operator fun invoke(
        releaseDate: String,
        sortBy: Sorting = Sorting.RELEASE_DATE_DESC,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ) = moviesRepository.getMoviesList(
        pageSize = pageSize,
        releaseDate = releaseDate,
        sortBy = sortBy
    )
}

private const val DEFAULT_PAGE_SIZE = 20