package com.alex.data.remote.datasource

import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse

interface MovieDbRemoteDataSource {
    suspend fun fetchMoviesList(page: Int): PagedResponse<MovieDto>
}