package com.alex.domain.movies.usecase

import com.alex.domain.movies.repository.MovieDbRepository

class GetMovieDetailsUseCase(
    private val movieDbRepository: MovieDbRepository
) {
    suspend operator fun invoke(movieId: Long) =
        try {
            Result.success(movieDbRepository.getMovieDetails(movieId))
        } catch (e: Exception){
            Result.failure(e)
        }
}