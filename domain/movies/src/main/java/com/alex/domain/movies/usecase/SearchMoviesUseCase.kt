package com.alex.domain.movies.usecase

import androidx.paging.PagingData
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.repository.MovieDbRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SearchMoviesUseCase(
    private val moviesRepository: MovieDbRepository
) {
    operator fun invoke(
        query: String,
        pageSize: Int = DEFAULT_PAGE_SIZE
    ): Flow<PagingData<Movie>> = when {
        query.isEmpty() -> flowOf(PagingData.empty())
        else -> moviesRepository.getMovieSuggestions(
            query = query,
            pageSize = pageSize
        )
    }
}

private const val DEFAULT_PAGE_SIZE = 20