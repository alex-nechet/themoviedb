package com.alex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.alex.data.mapper.toEntity
import com.alex.data.remote.dto.MovieDto
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.repository.MovieDbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

class MovieDbRepositoryImpl(
    private val remoteDataSource: PagingSource<Int, MovieDto>,
    private val coroutineContext: CoroutineContext = Dispatchers.IO
) : MovieDbRepository {
    override fun getMoviesList(pageSize: Int): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = { remoteDataSource }
        )
            .flow
            .map { data -> data.map { it.toEntity() } }
            .flowOn(coroutineContext)
}