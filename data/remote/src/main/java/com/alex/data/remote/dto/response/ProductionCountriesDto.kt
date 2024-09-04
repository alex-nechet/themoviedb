package com.alex.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountriesDto(
    val name: String?,

    @SerialName("iso_3166_1")
    val iso31661: String?,
)
