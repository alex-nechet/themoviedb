package com.alex.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    val page: Int,
    val results: List<T> = emptyList()
)