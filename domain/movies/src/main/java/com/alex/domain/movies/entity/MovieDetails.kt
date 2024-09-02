package com.alex.domain.movies.entity

data class MovieDetails(
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val overview: String,
)