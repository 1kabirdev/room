package com.room.dao

import androidx.room.*
import com.room.model.Users


@Dao
interface UsersDao {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<Users>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getById(id: Long): Users

    @Insert
    suspend fun insert(users: Users)

    @Delete
    suspend fun delete(user: Users)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}