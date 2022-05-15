package com.example.diplomapplication.room.timerPomadoro

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplomapplication.room.task.Utils.Convertor

@Database(entities = [TimerModel::class], version = 2, exportSchema = false)
@TypeConverters(Convertor::class)
abstract class TimerDatabase : RoomDatabase(){

    abstract fun myTimerDao(): TimerDao

    companion object{
        @Volatile
        var INSTANCE: TimerDatabase?= null

        fun getDatabaseNotes(context: Context): TimerDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val roomDataBaseInstance = Room.databaseBuilder(
                    context,
                    TimerDatabase::class.java,
                    "Timer")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = roomDataBaseInstance
                return roomDataBaseInstance
            }
        }

    }

}