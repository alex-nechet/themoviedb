package com.alex.domain.movies.repository

import androidx.paging.PagingData
import com.alex.domain.movies.entity.Movie
import kotlinx.coroutines.flow.Flow

interface MovieDbRepository {
    fun getMoviesList(pageSize: Int): Flow<PagingData<Movie>>
}