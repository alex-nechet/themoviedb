package com.alex.domain.movies.entity

data class Movie(
    val id: Long,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val overview: String,
)