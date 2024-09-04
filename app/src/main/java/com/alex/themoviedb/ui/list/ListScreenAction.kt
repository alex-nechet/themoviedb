package com.alex.themoviedb.ui.list

sealed class ListScreenAction {
    data object Retry : ListScreenAction()
    data class OpenDetails(val movieId: Long, val title: String) : ListScreenAction()
}