package com.alex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class GenresDto(
    val id: Int,
    val name: String?
)
