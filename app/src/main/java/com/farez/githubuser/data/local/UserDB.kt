package com.farez.githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class], version = 1
)
abstract class UserDB : RoomDatabase() {
    companion object {
        var INSTANCE: UserDB? = null

        fun getDataBase(c: Context): UserDB? {
            if (INSTANCE == null) {
                synchronized(UserDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        c.applicationContext, UserDB::class.java, "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun favUserDAO(): FavoriteUserDAO
}