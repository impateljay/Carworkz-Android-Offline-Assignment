package com.jay.carworkz.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.jay.carworkz.model.User

@Dao
interface UserDao {
    @Insert
    fun insertAll(users: List<User>)

    @Query("SELECT * FROM user")
    fun getUsers(): List<User>

    @Query("SELECT COUNT(*) FROM user")
    fun getUsersCount(): Int
}