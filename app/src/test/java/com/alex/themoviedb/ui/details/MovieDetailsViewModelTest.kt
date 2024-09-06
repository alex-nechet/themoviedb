package com.alex.themoviedb.ui.details

import com.alex.domain.movies.entity.Genres
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.usecase.GetMovieDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MovieDetailsViewModelTest {
    private val getMovieDetailsUseCase = mockk<GetMovieDetailsUseCase>()

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should return state loading when created`() {
        val viewModel = MovieDetailsViewModel(1, getMovieDetailsUseCase)
        assertTrue(viewModel.uiState.value is MovieDetailsUiState.Loading)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return state error when error`() = runTest {
        val testId = 1L
        val testErrorMessage = "Error"
        coEvery { getMovieDetailsUseCase(testId) } returns Result.failure(Exception(testErrorMessage))
        val viewModel = MovieDetailsViewModel(testId, getMovieDetailsUseCase)

        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is MovieDetailsUiState.Error)
        assertTrue((uiState as MovieDetailsUiState.Error).message == testErrorMessage)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `should return state success`() = runTest {
        val testId = 1L
        coEvery { getMovieDetailsUseCase(testId) } returns Result.success(testMovieDetails)
        val viewModel = MovieDetailsViewModel(testId, getMovieDetailsUseCase)

        advanceUntilIdle()

        val uiState = viewModel.uiState.first()
        assertTrue(uiState is MovieDetailsUiState.Success)
        assertTrue((uiState as MovieDetailsUiState.Success).movieDetails == testMovieDetails)
    }

    private val testMovieDetails = MovieDetails(
        id = 1,
        title = "Test Movie Details",
        overview = "Test Overview",
        posterPath = "Test Poster Path",
        backdropPath = "Test Backdrop Path",
        releaseDate = "Test Release Date",
        originalLanguage = "US",
        originalTitle = "Test Original Title",
        popularity = 1.0,
        voteAverage = 1.0,
        genres = listOf(Genres("Action")),
        productionCompanies = listOf(),
        productionCountries = listOf(),
        tagline = "Test Tagline",
        revenue = 1,
        budget = 1,
        originCountry = listOf("US")
    )
}