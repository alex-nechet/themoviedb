package com.alex.themoviedb.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alex.domain.movies.entity.MovieDetails
import com.alex.themoviedb.ui.common.ErrorContent
import com.alex.themoviedb.ui.common.PosterAndOverview

object MovieDetails {

    @Composable
    fun Screen(
        title: String,
        viewModel: MovieDetailsViewModel,
        onBackClick: () -> Unit
    ) {
        Content(
            title = title,
            state = viewModel.uiState.collectAsState().value,
            onAction = { action ->
                when (action) {
                    MovieDetailsAction.Refresh -> viewModel.getMovieDetails()
                    MovieDetailsAction.Back -> onBackClick()
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
            topBar = { TopBar(title = title, onBackClick = { onAction(MovieDetailsAction.Back) }) },
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
    private fun TopBar(
        title: String,
        onBackClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        TopAppBar(
            colors = TopAppBarDefaults
                .topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = modifier,
            title = { Text(title) },
            navigationIcon = {
                IconButton(
                    content = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    },
                    onClick = onBackClick,
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
                is MovieDetailsUiState.Success -> MovieDetailsMainContent(
                    movieDetails = state.movieDetails,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    @Composable
    private fun MovieDetailsMainContent(movieDetails: MovieDetails, modifier: Modifier = Modifier) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            Text(
                style = MaterialTheme.typography.headlineSmall,
                text = movieDetails.tagline
            )
            PosterAndOverview(
                posterPath = movieDetails.posterPath,
                overview = movieDetails.overview,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            DetailsItem("Release Date" to movieDetails.releaseDate)
            ChipRow(label = "Origin Country", chips = movieDetails.originCountry)
            ChipRow(label = "Genres", chips = movieDetails.genres.map { it.name })
            ChipRow(label = "Production", chips = movieDetails.productionCompanies.map { it.name })
            ChipRow(
                label = "Production Countries",
                chips = movieDetails.productionCountries.map { it.name }
            )
        }
    }

    @Composable
    private fun DetailsItem(values: Pair<String, String>) {
        Row(modifier = Modifier.padding(vertical = 8.dp)) {
            Text(modifier = Modifier.weight(1f), text = "${values.first}:")
            Text(modifier = Modifier, text = values.second)
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun ChipRow(
        label: String,
        chips: List<String>,
        modifier: Modifier = Modifier
    ) {
        Row(modifier = modifier) {
            Text(
                modifier = Modifier.padding(vertical = 12.dp),
                text = "${label}:"
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                chips.forEach {
                    AssistChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        label = { Text(text = it) },
                        onClick = {})
                }
            }
        }
    }
}