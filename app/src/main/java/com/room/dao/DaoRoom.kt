package com.room.dao

import androidx.room.*
import com.room.model.Tasks

@Dao
interface DaoRoom {

    /**
     * tasks db
     */
    @Query("SELECT * FROM tasks ORDER BY id DESC")
    suspend fun getAllTasks(): List<Tasks>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getByIdTasks(id: Long): Tasks

    @Insert
    suspend fun insertTasks(tasks: Tasks)

    @Delete
    suspend fun deleteTasks(tasks: Tasks)

}