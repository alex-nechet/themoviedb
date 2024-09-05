package com.alex.data.mapper

import com.alex.data.remote.dto.request.SortingParam
import com.alex.data.remote.dto.response.GenresDto
import com.alex.data.remote.dto.response.MovieDetailsDto
import com.alex.data.remote.dto.response.MovieDto
import com.alex.data.remote.dto.response.ProductionCompaniesDto
import com.alex.data.remote.dto.response.ProductionCountriesDto
import com.alex.domain.movies.entity.Genres
import com.alex.domain.movies.entity.Movie
import com.alex.domain.movies.entity.MovieDetails
import com.alex.domain.movies.entity.ProductionCompanies
import com.alex.domain.movies.entity.ProductionCountries
import com.alex.domain.movies.entity.Sorting


fun MovieDto.toEntity() = Movie(
    id = id,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    overview = overview.orEmpty()
)

fun GenresDto.toEntity() = Genres(name = name.orEmpty())

fun ProductionCompaniesDto.toEntity() = ProductionCompanies(
    name = name.orEmpty(),
    logoPath = logoPath.orEmpty()
)

fun ProductionCountriesDto.toEntity() = ProductionCountries(name = iso31661.orEmpty())

fun MovieDetailsDto.toEntity() = MovieDetails(
    id = id,
    budget = budget,
    genres = genres?.map { it.toEntity() }.orEmpty(),
    overview = overview.orEmpty(),
    popularity = popularity,
    revenue = revenue,
    tagline = tagline.orEmpty(),
    title = title.orEmpty(),
    backdropPath = backdropPath.orEmpty(),
    originCountry = originCountry.orEmpty(),
    originalLanguage = originalLanguage.orEmpty(),
    originalTitle = originalTitle.orEmpty(),
    posterPath = posterPath.orEmpty(),
    productionCompanies = productionCompanies?.map { it.toEntity() }.orEmpty(),
    productionCountries = productionCountries?.map { it.toEntity() }.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    voteAverage = voteAverage
)

fun Sorting.toDto() = when (this) {
    Sorting.RELEASE_DATE_DESC -> SortingParam.RELEASE_DATE_DESC
}

