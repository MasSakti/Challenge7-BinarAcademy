package com.mutawalli.challenge7.repository

import kotlinx.coroutines.runBlocking
import com.mutawalli.challenge7.data.local.UserDAO
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class UserRepositoryTest {
    private lateinit var dao: UserDAO
    private lateinit var repo: UserRepository

    @Before
    fun setUp() {
//        dao = mock()
        repo = UserRepository(dao)
    }

    @Test
    fun save()= runBlocking {
    }

    @Test
    fun update()= runBlocking {
    }

    @Test
    fun getUser()= runBlocking {
    }

    @Test
    fun verifyLogin()= runBlocking {
    }
}