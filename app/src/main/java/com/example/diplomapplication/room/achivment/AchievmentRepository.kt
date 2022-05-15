package com.example.diplomapplication.room.achivment

import androidx.lifecycle.LiveData

class AchievmentRepository(private val achievmentDao: AchievmentDao) {

    fun getAllAchievment():LiveData<List<Achievment>> = achievmentDao.getAchievment()

    fun insertAchievment(achievment: Achievment){
        achievmentDao.createAchievment(achievment)
    }

    fun deleteAchievment(id: Long){
        achievmentDao.deletegetAchievment(id)
    }

    fun updateAchievment(id: Long){
        achievmentDao.updategetAchievment(id)
    }

}