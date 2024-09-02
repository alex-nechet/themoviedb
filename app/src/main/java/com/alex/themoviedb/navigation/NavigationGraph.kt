package com.alex.themoviedb.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alex.themoviedb.ui.details.MovieDetails
import com.alex.themoviedb.ui.details.MovieDetailsViewModel
import com.alex.themoviedb.ui.list.MovieList
import com.alex.themoviedb.ui.list.MovieListViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.appGraph(navController: NavHostController) {
    composable<Route.List> {
        MovieList.Screen(
            viewModel = koinViewModel<MovieListViewModel>(),
            navigate = navController::navigate
        )
    }
    composable<Route.Details> {
        val route = it.toRoute<Route.Details>()
        MovieDetails.Screen(
            viewModel = koinViewModel<MovieDetailsViewModel> { parametersOf(route.movieId) }
        )
    }
}