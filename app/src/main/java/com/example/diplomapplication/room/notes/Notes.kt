package com.example.diplomapplication.room.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    var title:String,
    var description:String,
    var notes:String,
    val date:String,
    val priority:String,
    var IsPassword: Boolean,
    var password: String?,
    val uId: String
)