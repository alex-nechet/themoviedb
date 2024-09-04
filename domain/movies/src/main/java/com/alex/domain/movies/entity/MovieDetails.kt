package com.alex.domain.movies.entity

data class MovieDetails(
    val id: Int,
    val budget: Int?,
    val genres: List<Genres>,
    val overview: String,
    val popularity: Double?,
    val revenue: Int?,
    val tagline: String,
    val title: String?,
    val backdropPath: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanies>,
    val productionCountries: List<ProductionCountries>,
    val releaseDate: String,
    val voteAverage: Double?
)