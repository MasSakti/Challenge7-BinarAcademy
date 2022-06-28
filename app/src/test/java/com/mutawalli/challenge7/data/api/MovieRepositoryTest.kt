package com.mutawalli.challenge7.data.api

import kotlinx.coroutines.runBlocking
import com.mutawalli.challenge7.data.local.MovieLocalDataSource
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MovieRepositoryTest {
    private lateinit var remoteDS: com.mutawalli.challenge7.data.api.MovieRemoteDataSource
    private lateinit var localDS: MovieLocalDataSource
    private lateinit var repo: com.mutawalli.challenge7.data.api.MovieRepository

    @Before
    fun setUp() {
//        remoteDS = mock()
//        localDS = mock()
        repo = com.mutawalli.challenge7.data.api.MovieRepository(remoteDS, localDS)
    }

    @Test
    fun getPopularMovie() = runBlocking {
    }

    @Test
    fun getDetail()= runBlocking {
    }

    @Test
    fun insert()= runBlocking {
    }

    @Test
    fun delete()= runBlocking {
    }

    @Test
    fun getFavoriteUser()= runBlocking {
    }
}