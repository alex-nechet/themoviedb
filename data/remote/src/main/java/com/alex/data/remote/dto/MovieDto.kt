package com.alex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    @SerialName("original_title")
    val title : String?,

    @SerialName("poster_path")
    val posterPath : String?,

    @SerialName("release_date")
    val releaseDate: String?,

    val overview : String?,
)