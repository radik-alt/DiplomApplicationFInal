package com.example.diplomapplication.room.task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.diplomapplication.room.task.Utils.Convertor

@Database(entities = [Task::class], version = 5, exportSchema = false)
// конвертор date
@TypeConverters(Convertor::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun myNotesDao(): TaskDao

    companion object{
        @Volatile
        var INSTANCE: TaskDatabase?= null

        fun getDatabaseNotes(context: Context): TaskDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val roomDataBaseInstance = Room.databaseBuilder(
                    context,
                    TaskDatabase::class.java,
                    "TaskDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = roomDataBaseInstance
                return roomDataBaseInstance
            }
        }

    }

}