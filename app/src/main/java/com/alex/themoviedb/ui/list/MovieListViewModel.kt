package com.alex.themoviedb.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alex.domain.movies.usecase.GetMoviesUseCase

class MovieListViewModel(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val movies = getMoviesUseCase().cachedIn(viewModelScope)
}