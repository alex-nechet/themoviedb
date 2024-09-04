package com.alex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpokenLanguagesDto(
    val name: String?,

    @SerialName("english_name")
    var englishName: String?,

    @SerialName("iso_639_1")
    var iso6391: String?,
)