package com.edloca.fastask.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.edloca.fastask.database.entities.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}