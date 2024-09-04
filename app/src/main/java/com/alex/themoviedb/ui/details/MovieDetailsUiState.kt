package com.alex.themoviedb.ui.details

import com.alex.domain.movies.entity.MovieDetails

sealed class MovieDetailsUiState {
    data object Loading : MovieDetailsUiState()
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState()
    data class Error(val message: String) : MovieDetailsUiState()

}