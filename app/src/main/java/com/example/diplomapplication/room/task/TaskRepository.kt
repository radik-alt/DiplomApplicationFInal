package com.example.diplomapplication.room.task

import androidx.lifecycle.LiveData
import java.util.*

// реализация интерфейса и обращение к его методам
class TaskRepository(private val taskDao: TaskDao) {

    fun getAllTask(): LiveData<List<Task>> {
        return taskDao.getAllTask()
    }

    fun insertNotes(task: Task){
        taskDao.createNotes(task)
    }

    fun getCountTask():Int = taskDao.getCountTask()

    fun deleteNotes(id: Long){
        taskDao.deleteNotes(id)
    }

    fun updateNotes(task: Task){
        taskDao.updateNotes(task)
    }

}