package com.example.diplomapplication.room.timerPomadoro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.room.notes.NotesDatabase
import com.example.diplomapplication.room.notes.NotesRepository

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TimerRepository

    init {
        val dao = TimerDatabase.getDatabaseNotes(application).myTimerDao()
        repository = TimerRepository(dao)
    }

    fun insertTimer(timer: TimerModel){
        repository.insertNotes(timer)
    }

    fun getTimer():LiveData<List<TimerModel>>{
        return repository.getAllNotes()
    }

    fun getCountTimer():Int{
        return repository.getCountTimer()
    }

    fun deleteTimer(id:Long){
        repository.deleteNotes(id)
    }

    fun updateTimer(timer: TimerModel){
        repository.updateNotes(timer)
    }

}