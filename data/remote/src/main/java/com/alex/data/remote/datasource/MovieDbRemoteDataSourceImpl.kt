package com.alex.data.remote.datasource

import com.alex.data.remote.client
import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieDbRemoteDataSourceImpl(
    authToken: String,
    isDebuggable: Boolean,
    baseUrl: String = "https://api.themoviedb.org/3/",
) : MovieDbRemoteDataSource {
    private val client = client(
        baseUrl = baseUrl,
        authToken = authToken,
        isDebuggable = isDebuggable
    )

    override suspend fun fetchMoviesList(page: Int) = client
        .get("discover/movie"){
            url {
                parameter("page", page)
                parameter("sort_by", "primary_release_date.desc")
            }
        }
        .body<PagedResponse<MovieDto>>()
        .also { client.close() }
}