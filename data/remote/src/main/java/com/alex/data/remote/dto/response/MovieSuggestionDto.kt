package com.alex.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class MovieSuggestionDto(
    val id: Long,
    val title: String
)