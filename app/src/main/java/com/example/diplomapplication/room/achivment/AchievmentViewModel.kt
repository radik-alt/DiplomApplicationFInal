package com.example.diplomapplication.room.achivment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.room.notes.NotesDatabase
import com.example.diplomapplication.room.notes.NotesRepository

class AchievmentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AchievmentRepository

    init {
        val dao = AchievmentDatabase.getDatabaseNotes(application).myAchievmentDao()
        repository = AchievmentRepository(dao)
    }

    fun addAchievment(achievment: Achievment){
        repository.insertAchievment(achievment)
    }

    fun getAchiemvnet():LiveData<List<Achievment>>{
        return repository.getAllAchievment()
    }

    fun deleteAchievment(id:Long){
        repository.deleteAchievment(id)
    }

    fun updateAchievment(id: Long){
        repository.updateAchievment(id)
    }

}