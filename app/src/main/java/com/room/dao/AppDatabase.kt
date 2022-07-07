package com.room.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.room.model.Tasks
import com.room.model.Users

@Database(entities = [Users::class, Tasks::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): DaoRoom
    abstract fun tasksDao(): DaoRoom
}