package com.alex.data.remote.datasource

import com.alex.data.remote.client
import com.alex.data.remote.dto.MovieDetailsDto
import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse
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

    override suspend fun fetchMovieList(page: Int) =
        client.get("discover/movie") { parameter("page", page) }
            .body<PagedResponse<MovieDto>>().also { client.close() }

    override suspend fun fetchMovieDetails(movieId: Long): MovieDetailsDto =
        client.get("movie/${movieId}")
            .body<MovieDetailsDto>()
            .also { client.close() }
}