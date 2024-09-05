package com.alex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alex.data.mapper.toDto
import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.dto.response.MovieDto
import com.alex.data.remote.dto.response.PagedResponse
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.entity.Sorting

class MovieSuggestionPagingSource(
    private val remoteDataSource: MovieDbRemoteDataSource,
    private val query: String,
    ) : PagingSource<Int, MovieDto>() {
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
                remoteDataSource.fetchMovieSuggestions(
                    query = query,
                    page = page
                )

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