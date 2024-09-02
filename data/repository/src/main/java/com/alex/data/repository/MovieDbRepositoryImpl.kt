package com.alex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.alex.data.mapper.toEntity
import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.dto.MovieDto
import com.alex.data.remote.dto.PagedResponse
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MovieDbRepositoryImpl(
    private val remoteDataSource: MovieDbRemoteDataSource,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : MovieDbRepository {
    override fun getMoviesList(pageSize: Int): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { MovieListPagingSource(remoteDataSource) }
        )
            .flow
            .map { data -> data.map { it.toEntity() } }
            .flowOn(coroutineContext)


    override suspend fun getMovieDetails(movieId: Long): MovieDetails =
        withContext(coroutineContext) {
            remoteDataSource.fetchMovieDetails(movieId)
            return@withContext MovieDetails(0, "", "", "", "", "")
        }
}