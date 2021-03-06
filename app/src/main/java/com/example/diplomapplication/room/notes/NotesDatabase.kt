package com.example.diplomapplication.room.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 3, exportSchema = false)
abstract class NotesDatabase : RoomDatabase(){

    abstract fun myNotesDao(): NotesDao

    companion object{
        @Volatile
        var INSTANCE: NotesDatabase?= null

        fun getDatabaseNotes(context: Context): NotesDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val roomDataBaseInstance = Room.databaseBuilder(
                    context,
                    NotesDatabase::class.java,
                    "Notes")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

                INSTANCE = roomDataBaseInstance
                return roomDataBaseInstance
            }
        }

    }

}