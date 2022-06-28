package com.mutawalli.challenge7.data.local

import com.mutawalli.challenge7.data.local.favorite.MovieDAO
import com.mutawalli.challenge7.data.local.favorite.MovieEntity

class MovieLocalDataSource(private val movieDao: MovieDAO) {

    suspend fun insert(movie: MovieEntity) {
        movieDao.insert(movie)
    }

    suspend fun delete(movie: MovieEntity) = movieDao.delete(movie)

    suspend fun getFavoriteMovie(): List<MovieEntity> = movieDao.getFavorites()
}