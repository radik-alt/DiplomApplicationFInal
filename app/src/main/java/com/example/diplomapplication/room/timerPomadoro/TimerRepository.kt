package com.example.diplomapplication.room.timerPomadoro

import androidx.lifecycle.LiveData

class TimerRepository(private val timerDao: TimerDao) {

    fun getAllNotes():LiveData<List<TimerModel>> = timerDao.getTimerData()

    fun insertNotes(timer: TimerModel){
        timerDao.createNotes(timer)
    }

    fun getCountTimer() = timerDao.getCountTask()

    fun deleteNotes(id: Long){
        timerDao.deleteNotes(id)
    }

    fun updateNotes(timer: TimerModel){
        timerDao.updateNotes(timer)
    }

}