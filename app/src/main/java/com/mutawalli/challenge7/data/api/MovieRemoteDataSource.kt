package com.mutawalli.challenge7.data.api

import com.mutawalli.challenge7.network.MovieApiService
import javax.inject.Inject


class MovieRemoteDataSource @Inject constructor(private val service: MovieApiService, private val apiKey: String) {
    suspend fun getMovies(): List<com.mutawalli.challenge7.data.api.Movies>? {
        try {
            return service.getPopular(apiKey).results
        } catch (cause: Throwable) {
            throw com.mutawalli.challenge7.data.api.ErrorMovie("Data Gagal Diload", cause)
        }

    }

    suspend fun getDetail(id: Int): com.mutawalli.challenge7.data.api.Movies {
        try {
            return service.getDetail(movieId = id, api = apiKey).body()!!
        } catch (cause: Throwable) {
            throw com.mutawalli.challenge7.data.api.ErrorMovie(
                "Ada kesalahan saat load detail",
                cause
            )
        }
    }
}

class ErrorMovie(message: String, cause: Throwable?) : Throwable(message, cause)