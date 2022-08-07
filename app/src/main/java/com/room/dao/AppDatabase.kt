package com.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.room.model.Tasks

@Database(entities = [Tasks::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tasksDao(): DaoRoom
}