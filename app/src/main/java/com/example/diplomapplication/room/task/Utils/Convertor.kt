package com.example.diplomapplication.room.task.Utils

import androidx.room.TypeConverter
import java.util.*

class Convertor {

    // кноверторы из date в long и обратно
    @TypeConverter
    fun dateToTimestamp(date:Date?):Long? {
        return if (date == null) null else date.time
    }

    @TypeConverter
    fun fromTimestamp(value: Long?): Date?{
        return if (value == null) null else Date(value)
    }

}