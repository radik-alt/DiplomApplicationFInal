package com.example.diplomapplication.room.achivment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Achievment")
data class Achievment(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var nameAchievment: String,
    var descAChievement: String,
    var doing: Boolean,
    val uID: String
)
