package com.room.dao

import androidx.room.*
import com.room.model.Tasks
import com.room.model.Users


@Dao
interface DaoRoom {

    /**
     * users db
     */
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

    /**
     * tasks db
     */
    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Tasks>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getByIdTasks(id: Long): Tasks

    @Insert
    suspend fun insertTasks(tasks: Tasks)

    @Delete
    suspend fun deleteTasks(tasks: Tasks)

    @Query("DELETE FROM tasks")
    suspend fun deleteAllTasks()


}