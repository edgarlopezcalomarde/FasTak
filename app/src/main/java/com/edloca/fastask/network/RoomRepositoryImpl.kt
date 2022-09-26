package com.edloca.fastask.network

import com.edloca.fastask.database.TaskDao
import com.edloca.fastask.database.entities.TaskEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(private val dao: TaskDao) {

    fun getAllTasks() = dao.getAllTasks()

    fun addTask(taskName:String, taskContent:String) =
        dao.addTask(
            TaskEntity(
                name = taskName,
                content = taskContent,
                firstModification = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
        )

    fun getTaskById(id:Long) = dao.getTaskById(id)

    fun deleteTask(task:TaskEntity) = dao.deleteTask(task)

    fun updateTask(task:TaskEntity) = dao.updateTask(task)

}