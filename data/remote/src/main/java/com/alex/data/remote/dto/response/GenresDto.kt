package com.alex.data.remote.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    val id: Int,
    val name: String?
)
