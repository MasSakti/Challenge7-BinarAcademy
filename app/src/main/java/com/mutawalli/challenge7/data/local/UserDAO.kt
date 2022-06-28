package com.mutawalli.challenge7.data.local

import androidx.room.*

@Dao
interface UserDAO {
    @Insert
    suspend fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE email LIKE :email AND password LIKE :password")
    suspend fun readLogin(email: String, password: String): UserEntity

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int): UserEntity

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(user: UserEntity)
}