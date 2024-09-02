package com.alex.data.remote.datasource

import com.alex.data.remote.dto.MovieDetailsDto
import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse

interface MovieDbRemoteDataSource {
    suspend fun fetchMovieList(page: Int): PagedResponse<MovieDto>

    suspend fun fetchMovieDetails(movieId: Long): MovieDetailsDto
}