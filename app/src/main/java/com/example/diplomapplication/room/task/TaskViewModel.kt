package com.example.diplomapplication.room.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import java.util.*

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    //иницали
    private val repository: TaskRepository

    init {
        val daoTask = TaskDatabase.getDatabaseNotes(application).myNotesDao()
        repository = TaskRepository(daoTask)
    }

    fun getCountTask () : Int {
        return repository.getCountTask()
    }

    fun getTask(): LiveData<List<Task>>{
        return repository.getAllTask()
    }

    fun insertTask (task:Task) {
        repository.insertNotes(task)
    }

    fun updateTask(task: Task){
        repository.updateNotes(task)
    }

    fun deleteTask(id: Long){
        repository.deleteNotes(id)
    }

}