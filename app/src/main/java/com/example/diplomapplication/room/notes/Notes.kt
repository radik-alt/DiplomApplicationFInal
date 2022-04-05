package com.example.diplomapplication.room.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val title:String,
    val description:String,
    val notes:String,
    val date:String,
    val priority:String,
)