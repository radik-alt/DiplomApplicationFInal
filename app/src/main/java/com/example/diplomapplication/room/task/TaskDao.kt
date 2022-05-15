package com.example.diplomapplication.room.task

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplomapplication.room.task.Utils.Convertor
import java.util.*

@Dao
@TypeConverters(Convertor::class)
interface TaskDao {

    // загугли LiveData
    @Query("SELECT * FROM Task")
    fun getAllTask(): LiveData<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNotes(task: Task)

    @Query("DELETE FROM Task WHERE idTask =:id")
    fun deleteNotes(id:Long)

    // для достижений
    @Query("SELECT count(*) FROM Task")
    fun getCountTask() : Int

    @Update
    fun updateNotes(task: Task)


}