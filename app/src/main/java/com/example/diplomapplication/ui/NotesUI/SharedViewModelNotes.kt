package com.example.diplomapplication.ui.NotesUI

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diplomapplication.room.notes.Notes

class SharedViewModelNotes : ViewModel() {

    val notes = MutableLiveData<Notes>()

    fun setNotes(notesTemp: Notes) {
        notes.value = notesTemp
    }

    fun getNotes (): LiveData<Notes> {
        return notes
    }

}