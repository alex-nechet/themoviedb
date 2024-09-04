package com.alex.data.remote.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompaniesDto(
    val id: Int,
    val name: String?,

    @SerialName("logo_path")
    val logoPath: String?,

    @SerialName("origin_country")
    val originCountry: String?
)
