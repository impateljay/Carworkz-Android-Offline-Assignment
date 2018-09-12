package com.jay.carworkz.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jay.carworkz.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        private var database: AppDatabase? = null
        fun getDatabaseInstance(context: Context): AppDatabase {
            if (database == null) {
                database = Room.databaseBuilder(context, AppDatabase::class.java, "app.db").build()
            }
            return database as AppDatabase
        }
    }
}