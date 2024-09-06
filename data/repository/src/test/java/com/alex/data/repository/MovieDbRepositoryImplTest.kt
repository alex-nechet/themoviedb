package com.alex.data.repository

import androidx.paging.map
import com.alex.data.mapper.toEntity
import com.alex.data.remote.datasource.MovieDbRemoteDataSource
import com.alex.data.remote.dto.response.MovieDetailsDto
import com.alex.data.remote.dto.response.MovieDto
import com.alex.data.remote.dto.response.PagedResponse
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.entity.Sorting
import com.alex.domain.movies.repository.MovieDbRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDbRepositoryImplTest {
    private val remoteDataSource = mockk<MovieDbRemoteDataSource>()
    private lateinit var repository: MovieDbRepository

    @Before
    fun setUp() {
        repository = MovieDbRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineContext = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun `should fetch movie list`() = runTest {
        coEvery {
            remoteDataSource.fetchMovieList(
                any(),
                any(),
                any()
            )
        } returns PagedResponse(
            1,
            listOf(testMovieDto, testMovieDto)
        )

        val result = repository.getMoviesList(
            sortBy = Sorting.RELEASE_DATE_DESC,
            releaseDate = "Test Release Date",
            pageSize = 10
        ).first()

        result.map {
            assertTrue(it == testMovieEntity)
            it
        }
    }

    @Test
    fun `should search movies list`() = runTest {
        val query = "Test Query"
        coEvery {
            remoteDataSource.fetchMovieSuggestions(
                query = query,
                page = any(),
            )
        } returns PagedResponse(
            1,
            listOf(testMovieDto, testMovieDto)
        )

        val result = repository.getMovieSuggestions(
            query,
            pageSize = 10
        ).first()

        result.map {
            assertTrue(it == testMovieEntity)
            it
        }
    }

    @Test
    fun `should fetch movies details`() = runTest {
        val testMovieId = 1L
        coEvery {
            remoteDataSource.fetchMovieDetails(testMovieId)
        } returns testMovieDetails

        val result = repository.getMovieDetails(testMovieId)

        assertTrue(result == testMovieDetails.toEntity())
    }
}

private val testMovieDto = MovieDto(
    id = 1,
    title = "Test Movie",
    overview = "Test Overview",
    posterPath = "Test Poster Path",
    backdropPath = "Test Backdrop Path",
    releaseDate = "Test Release Date"
)

private val testMovieEntity = Movie(
    id = 1,
    title = "Test Movie",
    overview = "Test Overview",
    posterPath = "Test Poster Path",
    backdropPath = "Test Backdrop Path",
    releaseDate = "Test Release Date"
)

private val testMovieDetails = MovieDetailsDto(
    id = 1,
    title = "Test Movie Details",
    overview = "Test Overview",
    imdbId = null,
    posterPath = "Test Poster Path",
    backdropPath = "Test Backdrop Path",
    releaseDate = "Test Release Date",
    originalLanguage = "US",
    originalTitle = "Test Original Title",
    popularity = 1.0,
    voteAverage = 1.0,
    voteCount = 1,
    genres = null,
    productionCompanies = listOf(),
    productionCountries = listOf(),
    spokenLanguages = listOf(),
    runtime = 1,
    status = "Test Status",
    tagline = "Test Tagline",
    revenue = 1,
    budget = 1,
    homepage = "Test Homepage",
    adult = false,
    originCountry = null,
)