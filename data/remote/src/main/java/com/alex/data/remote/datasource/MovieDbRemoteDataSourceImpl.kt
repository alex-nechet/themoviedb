package com.alex.data.remote.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
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
) : PagingSource<Int, MovieDto>() {
    private val client = client(
        baseUrl = baseUrl,
        authToken = authToken,
        isDebuggable = isDebuggable
    )

    override fun getRefreshKey(state: PagingState<Int, MovieDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDto> {
        return try {
            val page = params.key ?: 1
            val response: PagedResponse<MovieDto> =
                client.get("discover/movie").body<PagedResponse<MovieDto>>()

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.results.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}