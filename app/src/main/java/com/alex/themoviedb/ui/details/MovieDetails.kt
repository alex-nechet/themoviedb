package com.alex.themoviedb.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.alex.domain.movies.entity.Genres
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.entity.ProductionCountries
import com.alex.themoviedb.R
import com.alex.themoviedb.theme.Dimens
import com.alex.themoviedb.ui.common.ErrorContent
import com.alex.themoviedb.ui.common.PosterAndOverview

object MovieDetails {

    @Composable
    fun Screen(
        viewModel: MovieDetailsViewModel,
        onBackClick: () -> Unit
    ) {
        Content(
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
        state: MovieDetailsUiState,
        onAction: (MovieDetailsAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        val title = when (state) {
            is MovieDetailsUiState.Success -> state.movieDetails.title.orEmpty()
            is MovieDetailsUiState.Error -> "Error"
            else -> ""
        }

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

                MovieDetailsUiState.Loading -> Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }

                is MovieDetailsUiState.Success -> MovieDetailsMainContent(
                    movieDetails = state.movieDetails,
                    modifier = Modifier.padding(Dimens.SPACE_16.dp)
                )
            }
        }
    }

    @Composable
    private fun MovieDetailsMainContent(
        movieDetails: MovieDetails,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier.verticalScroll(rememberScrollState())) {
            val budget = movieDetails.budget
            val revenue = movieDetails.revenue

            if (movieDetails.tagline.isNotEmpty()) {
                Text(
                    style = MaterialTheme.typography.headlineSmall,
                    text = movieDetails.tagline
                )
            }

            PosterAndOverview(
                posterPath = movieDetails.posterPath,
                overview = movieDetails.overview,
                modifier = Modifier.padding(vertical = Dimens.SPACE_16.dp)
            )

            if (movieDetails.releaseDate.isNotEmpty()) {
                DetailsItem(stringResource(R.string.release_date) to movieDetails.releaseDate)
            }

            if (budget != null && (budget > 0)) {
                DetailsItem(stringResource(R.string.budget) to movieDetails.budget.toString())
            }

            if (revenue != null && (revenue > 0)) {
                DetailsItem(stringResource(R.string.revenue) to movieDetails.revenue.toString())
            }

            ChipRow(
                label = stringResource(R.string.origin_country),
                chips = movieDetails.originCountry
            )

            ChipRow(
                label = stringResource(R.string.genres),
                chips = movieDetails.genres.map { it.name }
            )

            ChipRow(
                label = stringResource(R.string.production),
                chips = movieDetails.productionCompanies.map { it.name }
            )

            ChipRow(
                label = stringResource(R.string.production_countries),
                chips = movieDetails.productionCountries.map { it.name }
            )
        }
    }

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun ChipRow(
        label: String,
        chips: List<String>,
        modifier: Modifier = Modifier
    ) {
        if (chips.isNotEmpty()) {
            Row(modifier = modifier) {
                Text(
                    modifier = Modifier.padding(vertical = Dimens.SPACE_12.dp),
                    text = "${label}:"
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    chips.forEach {
                        AssistChip(
                            modifier = Modifier.padding(horizontal = Dimens.SPACE_4.dp),
                            label = { Text(text = it) },
                            onClick = {})
                    }
                }
            }
        }
    }

    @Composable
    private fun DetailsItem(values: Pair<String, String>) {
        Row(modifier = Modifier.padding(vertical = Dimens.SPACE_8.dp)) {
            Text(modifier = Modifier.weight(1f), text = "${values.first}:")
            Text(modifier = Modifier, text = values.second)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailPreview() {
    val movieDetails = MovieDetails(
        id = 0,
        budget = 100000,
        overview = "Really nice movie",
        tagline = "You must see it!",
        title = "Best movie ever",
        backdropPath = "",
        originCountry = listOf("DE"),
        originalLanguage = "DE",
        originalTitle = "Best movie ever",
        posterPath = "",
        popularity = 5.0,
        genres = listOf(Genres("Action")),
        releaseDate = "2024-09-09",
        productionCompanies = listOf(),
        productionCountries = listOf(ProductionCountries("Germany")),
        revenue = 1000000,
        voteAverage = 5.0
    )


    com.alex.themoviedb.ui.details.MovieDetails.Content(
        state = MovieDetailsUiState.Success(movieDetails),
        onAction = {}
    )
}