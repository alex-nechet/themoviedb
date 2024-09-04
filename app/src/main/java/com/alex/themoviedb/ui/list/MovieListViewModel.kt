package com.alex.themoviedb.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alex.domain.movies.usecase.GetMoviesUseCase
import com.alex.themoviedb.convertMillisToDate
import kotlinx.coroutines.flow.retry

class MovieListViewModel(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    val movies =
        getMoviesUseCase(
            releaseDate = System.currentTimeMillis().convertMillisToDate()
        ).cachedIn(viewModelScope)
}