package com.alex.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.alex.data.mapper.toEntity
import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.entity.MovieSuggestions
import com.alex.domain.movies.entity.Sorting
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
    override fun getMoviesList(
        sortBy: Sorting,
        releaseDate: String,
        pageSize: Int
    ): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(pageSize = pageSize),
            pagingSourceFactory = {
                MovieListPagingSource(
                    remoteDataSource = remoteDataSource,
                    sortBy = sortBy,
                    releaseDate = releaseDate
                )
            }
        )
            .flow
            .map { data -> data.map { it.toEntity() } }
            .flowOn(coroutineContext)


    override suspend fun getMovieDetails(movieId: Long): MovieDetails =
        withContext(coroutineContext) {
            remoteDataSource.fetchMovieDetails(movieId).toEntity()
        }

    override fun getMovieSuggestions(
        query: String, pageSize: Int
    ): Flow<PagingData<MovieSuggestions>> = Pager(
        config = PagingConfig(pageSize = pageSize),
        pagingSourceFactory = {
            MovieSuggestionPagingSource(
                remoteDataSource = remoteDataSource,
                query = query
            )
        }
    )
        .flow
        .map { data -> data.map { it.toEntity() } }
        .flowOn(coroutineContext)
}