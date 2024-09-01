package com.alex.data.mapper

import com.alex.data.remote.dto.MovieDto
import com.alex.domain.movies.entity.Movie


fun MovieDto.toEntity() = Movie(
    id = id,
    title = title.orEmpty(),
    posterPath = posterPath.orEmpty(),
    releaseDate = releaseDate.orEmpty(),
    overview = overview.orEmpty()
)