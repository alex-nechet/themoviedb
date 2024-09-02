package com.alex.themoviedb.navigation

import kotlinx.serialization.Serializable


sealed class Route {
    @Serializable
    data object List : Route()

    @Serializable
    data class Details(val movieId: Long) : Route()
}