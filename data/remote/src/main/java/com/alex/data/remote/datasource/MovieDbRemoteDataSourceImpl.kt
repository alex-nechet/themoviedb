package com.alex.data.remote.datasource

import com.alex.data.remote.client
import io.ktor.client.request.get

class MovieDbRemoteDataSourceImpl(
    private val baseUrl: String
) : MovieDbRemoteDataSource {

    override suspend fun fetchMoviesList() = client(baseUrl).get(""){
            url{

            }
        }
    }
}