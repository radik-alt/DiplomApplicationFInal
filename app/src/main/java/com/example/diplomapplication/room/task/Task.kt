package com.example.diplomapplication.room.task

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val idTask:Long?,
    val nameTask: String,
    val priorityTask: String,
    val dateNowTask: String,
    val dateCreatedTask: String,
    val completed: Boolean
)
