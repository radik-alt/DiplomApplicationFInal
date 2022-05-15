package com.example.diplomapplication.room.achivment

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Achievment::class], version = 2, exportSchema = false)
abstract class AchievmentDatabase : RoomDatabase(){

    abstract fun myAchievmentDao(): AchievmentDao

    companion object{
        @Volatile
        var INSTANCE: AchievmentDatabase?= null

        fun getDatabaseNotes(context: Context): AchievmentDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val roomDataBaseInstance = Room.databaseBuilder(
                    context,
                    AchievmentDatabase::class.java,
                    "Achievment")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = roomDataBaseInstance
                return roomDataBaseInstance
            }
        }

    }

}