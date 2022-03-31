package com.example.diplomapplication.ui.gallery.NotesAdd

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.diplomapplication.room.Notes
import com.example.diplomapplication.room.NotesDatabase
import com.example.diplomapplication.room.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {

    val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseNotes(application).myNotesDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes:Notes){
        repository.insertNotes(notes)
    }

    fun getNotes():LiveData<List<Notes>>{
        return repository.getAllNotes()
    }

    fun getHighNotes():LiveData<List<Notes>> = repository.getHighNotes()

    fun getMiddleNotes():LiveData<List<Notes>> = repository.getMiddleNotes()

    fun getLowNotes():LiveData<List<Notes>> = repository.getLowNotes()

    fun deleteNotes(id:Long){
        repository.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        repository.updateNotes(notes)
    }

}