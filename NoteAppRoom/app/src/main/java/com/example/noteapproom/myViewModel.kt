package com.example.noteapproom

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapproom.data.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class myViewModel(application: Application): AndroidViewModel(application) {
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()

    private var db: FirebaseFirestore = Firebase.firestore



    fun getNotes(): LiveData<List<Note>>{
        return notes
    }

    fun addNote(noteText: String){
        CoroutineScope(Dispatchers.IO).launch {

        val newNote = hashMapOf(
            "noteText" to noteText,
        )
        db.collection("notes").add(newNote)
        getData()
        }
    }

     fun getData() {
         db.collection("notes")
             .get()
             .addOnSuccessListener { result ->
                 val tempNotes = arrayListOf<Note>()
                 for (document in result) {
                     document.data.map { (key, value) -> tempNotes.add(Note(document.id, value.toString())) }
                 }
                 notes.postValue(tempNotes)
             }
             .addOnFailureListener { exception ->
                 Log.w("er99", "Error getting documents.", exception)
             }
    }

    fun editNote(noteID: String, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        println("DB: ${document.id}")
                        println("LOCAL: $noteID")
                        if(document.id == noteID){
                            db.collection("notes").document(noteID).update("noteText", noteText)
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("err99", "Error getting documents.", exception)
                }
        }
    }

    fun deleteNote(noteID: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        println("DB: ${document.id}")
                        println("LOCAL: $noteID")
                        if(document.id == noteID){
                            db.collection("notes").document(noteID).delete()
                        }
                    }
                    getData()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "Error getting documents.", exception)
                }
        }
    }
}