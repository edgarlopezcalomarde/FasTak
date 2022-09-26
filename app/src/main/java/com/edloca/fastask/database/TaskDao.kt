package com.edloca.fastask.database

import androidx.room.*
import com.edloca.fastask.database.entities.TaskEntity

@Dao
interface TaskDao:CustomDao {
    @Query("SELECT * FROM task_entity")
    override fun getAllTasks(): MutableList<TaskEntity>

    @Insert
    override fun addTask(taskEntity : TaskEntity):Long

    @Query("SELECT * FROM task_entity WHERE id LIKE :id")
    override fun getTaskById(id: Long): TaskEntity

    @Update
    override fun updateTask(taskEntity: TaskEntity):Int //Number of affected row.

    @Delete
    override fun deleteTask(taskEntity: TaskEntity):Int
}