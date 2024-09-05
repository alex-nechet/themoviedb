package com.alex.data.remote.datasource

import com.alex.data.remote.client
import com.alex.data.remote.dto.request.SortingParam
import com.alex.data.remote.dto.response.MovieDetailsDto
import com.alex.data.remote.dto.response.MovieDto
import com.alex.data.remote.dto.response.PagedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieDbRemoteDataSourceImpl(
    private val authToken: String,
    private val isDebuggable: Boolean,
    private val baseUrl: String,
) : MovieDbRemoteDataSource {

    private val client: HttpClient
        get() = client(
            baseUrl = baseUrl,
            authToken = authToken,
            isDebuggable = isDebuggable
        )

    override suspend fun fetchMovieList(
        sortBy: SortingParam,
        releaseDate: String,
        page: Int
    ) = client.get("discover/movie") {
        parameter("page", page)
        parameter("sort_by", sortBy.value)
        parameter("primary_release_date.lte", releaseDate)
    }
        .body<PagedResponse<MovieDto>>()
        .also { client.close() }

    override suspend fun fetchMovieSuggestions(
        query: String,
        page: Int
    ) = client.get("search/movie") {
        parameter("page", page)
        parameter("query", query)
    }
        .body<PagedResponse<MovieDto>>()
        .also { client.close() }

    override suspend fun fetchMovieDetails(
        movieId: Long
    ) = client.get("movie/${movieId}")
        .body<MovieDetailsDto>()
        .also { client.close() }
}