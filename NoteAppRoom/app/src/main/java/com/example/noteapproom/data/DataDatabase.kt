package com.example.noteapproom.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Note::class],version = 1,exportSchema = false)

abstract class DataDatabase: RoomDatabase() {

    companion object{
        var instant: DataDatabase?=null
        fun getinstant(context: Context): DataDatabase {
            if(instant !=null)
            {
                return instant as DataDatabase
            }
            instant = Room.databaseBuilder(context, DataDatabase::class.java,"myNote").run{
                allowMainThreadQueries() }.build()
            return instant as DataDatabase
        }
    }
    abstract fun DataDao(): NoteDao
}
