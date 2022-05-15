package com.example.diplomapplication.room.timerPomadoro

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.diplomapplication.room.task.Utils.Convertor
import java.util.*


@Entity (tableName = "Timer")
data class TimerModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val timerWork: Long,
    val timerRelax: Long,
    val quantityTimer: Int,
    @TypeConverters(Convertor::class)
    var dateTime: Date?,
    val uID: String
)
