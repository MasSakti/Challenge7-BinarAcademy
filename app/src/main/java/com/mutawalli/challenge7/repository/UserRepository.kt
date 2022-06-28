package com.mutawalli.challenge7.repository

import com.mutawalli.challenge7.data.local.UserDAO
import com.mutawalli.challenge7.data.local.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDAO) {
    suspend fun save(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    suspend fun update(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.update(user)
        }
    }

    suspend fun getUser(id: Int): UserEntity {
        return userDao.getUser(id)
    }

    suspend fun verifyLogin(email: String, password: String): UserEntity {
        return userDao.readLogin(email, password)
    }
}