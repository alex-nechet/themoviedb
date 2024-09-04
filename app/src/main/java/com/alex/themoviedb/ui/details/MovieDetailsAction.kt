package com.alex.themoviedb.ui.details

sealed class MovieDetailsAction {
    data object Refresh : MovieDetailsAction()
}