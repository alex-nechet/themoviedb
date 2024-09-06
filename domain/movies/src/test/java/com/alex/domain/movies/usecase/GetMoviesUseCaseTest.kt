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

class GetMoviesUseCaseTest {
    private val movieDbRepository = mockk<MovieDbRepository>()

    @Test
    fun `should fetch movies list`() = runTest {
        val testReleaseDate = "2023-01-01"
        val testData =
            PagingData.from(listOf(testMovie, testMovie.copy(releaseDate = testReleaseDate)))
        every {
            movieDbRepository.getMoviesList(any(), testReleaseDate, any())
        } returns flowOf(testData)
        val useCase = GetMoviesUseCase(movieDbRepository)

        val result: PagingData<Movie> = useCase(releaseDate = testReleaseDate).first()

        assertTrue(result != PagingData.empty<Movie>())
        assertTrue(result != PagingData.from(listOf(testMovie)))
        assertEquals(result, testData)
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