package com.example.diplomapplication.ui.ToDoIstUI.Interface

import com.example.diplomapplication.room.task.Task

interface ToDoClickTask {

    fun getTask(task: Task, choose:Int)
}