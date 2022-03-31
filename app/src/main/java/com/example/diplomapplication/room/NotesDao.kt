package com.example.diplomapplication.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes")
    fun getNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority='Max'")
    fun getHighNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority='Middle'")
    fun getMiddleNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority='Low'")
    fun getLowNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNotes(notes: Notes)

    @Query("DELETE FROM Notes WHERE id =:id")
    fun deleteNotes(id:Long)

    @Update
    fun updateNotes(notes: Notes)
}