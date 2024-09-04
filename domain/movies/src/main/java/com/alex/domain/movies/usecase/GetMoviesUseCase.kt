package com.alex.domain.movies.usecase

import com.alex.domain.movies.entity.Sorting
import com.alex.domain.movies.repository.MovieDbRepository

class GetMoviesUseCase(
    private val moviesRepository: MovieDbRepository
) {
    operator fun invoke(
        keywords: String = "",
        releaseDate: String,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ) = moviesRepository.getMoviesList(
        keywords = keywords,
        pageSize = pageSize,
        releaseDate = releaseDate,
        sortBy = Sorting.RELEASE_DATE_DESC
    )
}

private const val DEFAULT_PAGE_SIZE = 20