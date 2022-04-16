package com.example.diplomapplication.ui.gallery.adapterNotes

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.ItemNotesBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.ui.gallery.Interface.onClikcNotes

class AdapterNotes(val context:Context, val notes: List<Notes>, private var onClickNotes: onClikcNotes) : RecyclerView.Adapter<ViewHolderNotes>(){

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
            "Max" -> {
                bg.setBackgroundResource(R.color.max_priority)
            }
            "Middle" -> {
                bg.setBackgroundResource(R.color.middle_priority)
            }
            "Low" -> {
                bg.setBackgroundResource(R.color.low_priority)
            }
        }


        holder.itemView.setOnClickListener {
            onClickNotes.getSelectNotes(notes[position])
//            Navigation.findNavController(it).navigate(R.id.action_nav_gallery_to_passwordFragment)
        }

    }

    override fun getItemCount(): Int = notes.size

}