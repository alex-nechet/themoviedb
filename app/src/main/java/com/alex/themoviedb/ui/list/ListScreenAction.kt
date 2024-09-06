package com.alex.themoviedb.ui.list

sealed class ListScreenAction {
    data object Retry : ListScreenAction()
    data object CloseSuggestions : ListScreenAction()
    data class OpenDetails(val movieId: Long) : ListScreenAction()
    data class Search(val query: String) : ListScreenAction()
}