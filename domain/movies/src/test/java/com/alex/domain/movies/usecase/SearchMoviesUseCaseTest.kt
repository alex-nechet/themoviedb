package com.alex.domain.movies.usecase

import androidx.paging.PagingData
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.repository.MovieDbRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SearchMoviesUseCaseTest {
    private val movieDbRepository = mockk<MovieDbRepository>()

    @Test
    fun `should search movies`() = runTest {
        val testTitle = "Test Title"
        val testData =
            listOf(testMovie, testMovie.copy(title = testTitle))

        val testPagingData = PagingData.from(testData)

        every {
            movieDbRepository.getMovieSuggestions(testTitle, any())
        } returns flowOf(testPagingData)
        val useCase = SearchMoviesUseCase(movieDbRepository)

        val result: PagingData<Movie> = useCase(query = testTitle).first()

        assertTrue(result != PagingData.empty<Movie>())
        assertTrue(result != PagingData.from(listOf(testMovie, testMovie)))
        assertEquals(result, testPagingData)
    }

    @Test
    fun `should return empty `() = runTest {
        val testTitle = "Test Title"
        val testData =
            listOf(testMovie.copy(title = ""), testMovie.copy(title = ""))
        val testPagingData = PagingData.from(testData)
        val emptyPagingData = PagingData.empty<Movie>()

        every {
            movieDbRepository.getMovieSuggestions(testTitle, any())
        } returns flowOf(emptyPagingData)

        every {
            movieDbRepository.getMovieSuggestions("", any())
        } returns flowOf(testPagingData)

        val useCase = SearchMoviesUseCase(movieDbRepository)

        val result: PagingData<Movie> = useCase(query = testTitle).first()

        assertTrue(result != testPagingData)
        assertEquals(result, emptyPagingData)
    }


    private val testMovie = Movie(
        id = 1,
        title = "Test Movie",
        overview = "Test Overview",
        posterPath = "Test Poster Path",
        backdropPath = "Test Backdrop Path",
        releaseDate = "Test Release Date"
    )
}