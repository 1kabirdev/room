package com.room

import android.app.Application
import androidx.room.Room
import com.room.dao.AppDatabase


class App : Application() {
    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "tasks_database"
        ).build()
    }

    fun getDatabase(): AppDatabase {
        return database
    }
}