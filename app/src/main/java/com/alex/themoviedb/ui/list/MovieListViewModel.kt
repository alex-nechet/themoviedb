package com.alex.themoviedb.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alex.domain.movies.usecase.SearchMoviesUseCase
import com.alex.domain.movies.usecase.GetMoviesUseCase
import com.alex.themoviedb.convertMillisToDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class MovieListViewModel(
    getMoviesUseCase: GetMoviesUseCase,
    searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    val movies = getMoviesUseCase(
        releaseDate = System.currentTimeMillis().convertMillisToDate()
    ).cachedIn(viewModelScope)


    val query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestions = query.flatMapLatest { searchMoviesUseCase(it) }.cachedIn(viewModelScope)


    init {
        query.value = ""
    }
}