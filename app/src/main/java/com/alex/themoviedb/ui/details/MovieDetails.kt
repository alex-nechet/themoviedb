package com.alex.themoviedb.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.alex.domain.movies.entity.MovieDetails
import com.alex.themoviedb.ui.common.ErrorContent

object MovieDetails {

    @Composable
    fun Screen(
        title: String,
        viewModel: MovieDetailsViewModel
    ) {
        Content(
            title = title,
            state = viewModel.uiState.collectAsState().value,
            onAction = { action ->
                when (action) {
                    MovieDetailsAction.Refresh -> viewModel::getMovieDetails
                }
            })
    }

    @Composable
    fun Content(
        title: String,
        state: MovieDetailsUiState,
        onAction: (MovieDetailsAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Scaffold(
            modifier = modifier,
            topBar = { TopBar(title = title) },
            content = { paddingValues ->
                MainContent(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopBar(title: String, modifier: Modifier = Modifier) {
        TopAppBar(
            modifier = modifier,
            title = { Text(title) },
            navigationIcon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        )
    }

    @Composable
    private fun MainContent(
        state: MovieDetailsUiState,
        onAction: (MovieDetailsAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        Box(modifier = modifier) {
            when (state) {
                is MovieDetailsUiState.Error -> ErrorContent(
                    errorMessage = state.message,
                    onRetryClick = { onAction(MovieDetailsAction.Refresh) }
                )

                MovieDetailsUiState.Loading -> CircularProgressIndicator()
                is MovieDetailsUiState.Success -> MovieDetailsMainContent(state.movieDetails)
            }
        }
    }

    @Composable
    private fun MovieDetailsMainContent(movieDetails: MovieDetails) {
        Column {

        }
    }
}