package com.example.diplomapplication.room.achivment

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AchievmentDao {

    @Query("SELECT * FROM Achievment")
    fun getAchievment(): LiveData<List<Achievment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAchievment(achievment: Achievment)

    @Query("DELETE FROM Achievment WHERE id =:id")
    fun deletegetAchievment(id:Long)

    @Query("UPDATE Achievment SET doing = 1 WHERE id =:id")
    fun updategetAchievment(id: Long)
}