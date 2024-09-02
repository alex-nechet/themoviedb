package com.alex.themoviedb.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alex.domain.movies.usecase.GetMoviesUseCase
import kotlinx.coroutines.flow.retry

class MovieListViewModel(
    getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {
    val movies = getMoviesUseCase().cachedIn(viewModelScope)
}