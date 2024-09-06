package com.alex.domain.movies.usecase

import com.alex.domain.movies.entity.Genres
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.repository.MovieDbRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test


class GetMovieDetailsUseCaseTest {
    private val movieDbRepository = mockk<MovieDbRepository>()


    @Test
    fun `should fetch movie details`() = runTest {
        val testMovieId = 1L
        coEvery { movieDbRepository.getMovieDetails(testMovieId) } returns testMovieDetails
        val useCase = GetMovieDetailsUseCase(movieDbRepository)

        val result: Result<MovieDetails> = useCase(testMovieId)

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull() == testMovieDetails)
    }

    @Test
    fun `should handle movie details network error`() = runTest {
        val testMovieId = 1L
        val exceptionMessage = "Network Error"
        coEvery { movieDbRepository.getMovieDetails(testMovieId) } throws Exception(exceptionMessage)

        val useCase = GetMovieDetailsUseCase(movieDbRepository)

        val result: Result<MovieDetails> = useCase(testMovieId)

        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull()?.message == exceptionMessage)
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