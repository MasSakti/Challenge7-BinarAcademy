package com.mutawalli.challenge7.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mutawalli.challenge7.data.local.UserDAO
import com.mutawalli.challenge7.data.local.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "user"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }
    }
}