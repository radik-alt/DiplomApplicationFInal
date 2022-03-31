package com.example.diplomapplication.Model


data class Task(
    val id:Long,
    val name: String,
    val description: String,
    val priority: Int,
    val date: String
)
