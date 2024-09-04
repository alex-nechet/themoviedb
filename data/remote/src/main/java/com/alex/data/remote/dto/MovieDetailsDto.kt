package com.alex.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val adult: Boolean?,
    val budget: Int?,
    val genres: List<GenresDto>?,
    val homepage: String?,
    val overview: String?,
    val popularity: Double?,
    val revenue: Int?,
    val runtime: Int?,
    val status: String?,
    val tagline: String?,
    val title: String?,

    @SerialName("backdrop_path")
    val backdropPath: String?,

    @SerialName("belongs_to_collection")
    val belongsToCollection: String?,

    @SerialName("imdb_id")
    val imdbId: String?,

    @SerialName("origin_country")
    val originCountry: List<String>?,

    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("original_title")
    val originalTitle: String?,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompaniesDto>?,

    @SerialName("production_countries")
    val productionCountries: List<ProductionCountriesDto>?,

    @SerialName("release_date")
    val releaseDate: String?,

    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguagesDto>?,

    @SerialName("vote_average")
    val voteAverage: Double?,

    @SerialName("vote_count")
    val voteCount: Int?
)



