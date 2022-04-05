package com.example.diplomapplication.room.notes

import androidx.lifecycle.LiveData

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotes():LiveData<List<Notes>> = notesDao.getNotes()

    fun getHighNotes():LiveData<List<Notes>> = notesDao.getHighNotes()

    fun getMiddleNotes():LiveData<List<Notes>> = notesDao.getMiddleNotes()

    fun getLowNotes():LiveData<List<Notes>> = notesDao.getLowNotes()


    fun insertNotes(notes: Notes){
        notesDao.createNotes(notes)
    }

    fun deleteNotes(id: Long){
        notesDao.deleteNotes(id)
    }

    fun updateNotes(notes: Notes){
        notesDao.updateNotes(notes)
    }

}