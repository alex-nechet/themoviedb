package com.alex.themoviedb.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex.domain.movies.usecase.GetMovieDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieId: Long,
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase
) : ViewModel() {

    private val uiStateInternal: MutableStateFlow<MovieDetailsUiState> =
        MutableStateFlow(MovieDetailsUiState.Loading)

    val uiState: StateFlow<MovieDetailsUiState> = uiStateInternal.asStateFlow()

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            uiStateInternal.value = MovieDetailsUiState.Loading
            getMovieDetailsUseCase(movieId)
                .onSuccess { uiStateInternal.value = MovieDetailsUiState.Success(it) }
                .onFailure {
                    val message = it.message.orEmpty()
                    uiStateInternal.value = MovieDetailsUiState.Error(message)
                }
        }
    }
}