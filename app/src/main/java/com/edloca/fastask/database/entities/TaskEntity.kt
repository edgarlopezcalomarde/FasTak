package com.edloca.fastask.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_entity")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int =0,
    var name:String = "",
    var content:String = "",
    var isReady:Boolean = false,
    var firstModification: String = "",
    var lastModification:String = ""
)