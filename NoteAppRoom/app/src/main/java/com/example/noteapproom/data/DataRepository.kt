package com.example.noteapproom.data


import androidx.lifecycle.LiveData

class DataRepository(private val noteDao: NoteDao) {

    val getNotes: LiveData<List<Note>> = noteDao.getAllUserInfo()

    suspend fun addNote(note: Note){
        noteDao.insertnote(note)
    }

    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

}