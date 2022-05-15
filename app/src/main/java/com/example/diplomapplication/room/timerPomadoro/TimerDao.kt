package com.example.diplomapplication.room.timerPomadoro

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.diplomapplication.room.task.Utils.Convertor


@Dao
@TypeConverters(Convertor::class)
interface TimerDao {

    @Query("SELECT * FROM Timer")
    fun getTimerData(): LiveData<List<TimerModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createNotes(notes: TimerModel)

    @Query("DELETE FROM Timer WHERE id =:id")
    fun deleteNotes(id:Long)

    @Query("SELECT count(*) FROM Timer")
    fun getCountTask() : Int

    @Update
    fun updateNotes(timer: TimerModel)

}