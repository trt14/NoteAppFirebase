package com.example.noteapproom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item.view.*

class RecycleView(val activity: MainActivity) : RecyclerView.Adapter<RecycleView.recyclerViewHolder>() {
    private var notes = emptyList<Note>()

    class recyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): recyclerViewHolder {
        return recyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false))
    }

    override fun onBindViewHolder(holder: recyclerViewHolder, position: Int) {
        val note = notes[position]

        holder.itemView.apply {

            tvtxt.text = note.noteText

            ibEditNote.setOnClickListener {
                activity.update(note.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.mainViewModel.deleteNote(note.id)
            }
        }}


    override fun getItemCount()=notes.size

    fun update(notes: List<Note>){
        println("UPDATING DATA")
        this.notes = notes
        notifyDataSetChanged()
    }
}

