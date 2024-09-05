package com.alex.data.remote.datasource

import com.alex.data.remote.dto.request.SortingParam
import com.alex.data.remote.dto.response.MovieDetailsDto
import com.alex.data.remote.dto.response.MovieDto
import com.alex.data.remote.dto.response.MovieSuggestionDto
import com.alex.data.remote.dto.response.PagedResponse

interface MovieDbRemoteDataSource {
    suspend fun fetchMovieDetails(movieId: Long): MovieDetailsDto
    suspend fun fetchMovieList(
        sortBy: SortingParam,
        releaseDate: String,
        page: Int
    ): PagedResponse<MovieDto>

    suspend fun fetchMovieSuggestions(
        query: String,
        page: Int
    ): PagedResponse<MovieSuggestionDto>
}