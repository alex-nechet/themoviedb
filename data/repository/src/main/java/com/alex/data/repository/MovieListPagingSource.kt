package com.alex.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse

class MovieListPagingSource(
    private val remoteDataSource: MovieDbRemoteDataSource
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
            val response: PagedResponse<MovieDto> = remoteDataSource.fetchMovieList(page)

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