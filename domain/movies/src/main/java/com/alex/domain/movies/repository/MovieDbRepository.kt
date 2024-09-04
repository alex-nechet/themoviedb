package com.alex.domain.movies.repository

import androidx.paging.PagingData
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.entity.Sorting
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    suspend fun getMovieDetails(movieId: Long): MovieDetails
    fun getMoviesList(
        keywords: String,
        sortBy: Sorting,
        releaseDate: String,
        pageSize: Int
    ): Flow<PagingData<Movie>>
}