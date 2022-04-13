package com.example.diplomapplication.room.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diplomapplication.room.task.Utils.Convertor
import com.example.diplomapplication.room.task.Utils.Priority
import java.util.*

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val idTask:Long?,
    var nameTask: String,
    var priorityTask: Priority,
    val dateNowTask: Date?,
    @TypeConverters(Convertor::class)
    var dateCreatedTask: Date?,
    val completed: Boolean
)
