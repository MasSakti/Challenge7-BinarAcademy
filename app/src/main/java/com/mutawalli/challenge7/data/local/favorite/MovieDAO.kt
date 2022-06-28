package com.mutawalli.challenge7.data.local.favorite

import androidx.room.*

@Dao
interface MovieDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userEntity: MovieEntity)

    @Delete
    suspend fun delete(userEntity: MovieEntity)

    @Query("SELECT * from MovieEntity")
    suspend fun getFavorites(): List<MovieEntity>
}