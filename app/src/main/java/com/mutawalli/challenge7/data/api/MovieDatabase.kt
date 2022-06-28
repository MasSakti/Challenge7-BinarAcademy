package com.mutawalli.challenge7.data.api

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mutawalli.challenge7.data.local.UserDAO
import com.mutawalli.challenge7.data.local.UserEntity
import com.mutawalli.challenge7.data.local.favorite.MovieDAO
import com.mutawalli.challenge7.data.local.favorite.MovieEntity

@Database(entities = [UserEntity::class, MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO
    abstract fun movieDao(): MovieDAO
}