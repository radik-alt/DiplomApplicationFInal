package com.example.diplomapplication.ui.gallery.adapterNotes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.ItemNotesBinding
import com.example.diplomapplication.room.Notes
import com.example.diplomapplication.ui.gallery.GalleryFragment
import com.example.diplomapplication.ui.gallery.NotesAdd.AppendNotesFragment

class AdapterNotes(val context:Context, val notes: List<Notes>) : RecyclerView.Adapter<ViewHolderNotes>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNotes {
        val view = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderNotes(view)
    }

    override fun onBindViewHolder(holder: ViewHolderNotes, position: Int) {
        var data = notes[position]
        holder.titleNotes.text = data.title
        holder.textNotes.text = data.notes
        holder.dateNotes.text = data.date

        var bg = holder.bgNotes
        when (data.priority){
            "MAX" -> {
                bg.setBackgroundResource(R.color.max_priority)
            }
            "MIDDLE" -> {
                bg.setBackgroundResource(R.color.middle_priority)
            }
            "LOW" -> {
                bg.setBackgroundResource(R.color.low_priority)
            }
        }

        holder.itemView.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.editNotesFragment)
        }

    }

    override fun getItemCount(): Int = notes.size

}