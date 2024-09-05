package com.alex.themoviedb.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.alex.domain.movies.usecase.SearchMoviesUseCase
import com.alex.domain.movies.usecase.GetMoviesUseCase
import com.alex.themoviedb.convertMillisToDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class MovieListViewModel(
    getMoviesUseCase: GetMoviesUseCase,
    searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {
    private val query = MutableStateFlow("")
    val queryState = query.asStateFlow()

    val movies = getMoviesUseCase(
        releaseDate = System.currentTimeMillis().convertMillisToDate()
    ).cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResults = query.debounce(DEBOUNCE_MILLIS)
        .flatMapLatest { searchMoviesUseCase(it) }
        .cachedIn(viewModelScope)

    fun setQuery(query: String) {
        this@MovieListViewModel.query.tryEmit(query)
    }
}

private const val DEBOUNCE_MILLIS = 500L