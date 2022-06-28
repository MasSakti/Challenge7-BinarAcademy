package com.mutawalli.challenge7.data.api

import com.mutawalli.challenge7.data.local.MovieLocalDataSource
import com.mutawalli.challenge7.data.local.favorite.MovieEntity
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: com.mutawalli.challenge7.data.api.MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) {
    suspend fun getPopularMovie(): List<com.mutawalli.challenge7.data.api.Movies>? {
        return movieRemoteDataSource.getMovies()
    }

    suspend fun getDetail(id: Int): com.mutawalli.challenge7.data.api.Movies {
        return movieRemoteDataSource.getDetail(id)
    }

    suspend fun insert(movie: MovieEntity) = localDataSource.insert(movie)

    suspend fun delete(movie: MovieEntity) = localDataSource.delete(movie)

    suspend fun getFavoriteUser(): List<MovieEntity> = localDataSource.getFavoriteMovie()

    companion object {
        @Volatile
        private var instance: com.mutawalli.challenge7.data.api.MovieRepository? = null
        fun getInstance(
            remoteDataSource: com.mutawalli.challenge7.data.api.MovieRemoteDataSource,
            localDataSource: MovieLocalDataSource
        ): com.mutawalli.challenge7.data.api.MovieRepository =
            com.mutawalli.challenge7.data.api.MovieRepository.Companion.instance ?: synchronized(this) {
                com.mutawalli.challenge7.data.api.MovieRepository.Companion.instance
                    ?: com.mutawalli.challenge7.data.api.MovieRepository(
                        remoteDataSource,
                        localDataSource
                    )
            }.also { com.mutawalli.challenge7.data.api.MovieRepository.Companion.instance = it }
    }
}