package com.example.diplomapplication.ui.NotesUI.adapterNotes

import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.databinding.ItemNotesBinding

class ViewHolderNotes(binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {

    // инициализируем заголовк, описание, текс и background (RelativeLayout) заметки
    val titleNotes = binding.titleNotes
    val textNotes = binding.textNotes
    val dateNotes = binding.dateNotes
    val bgNotes = binding.bgNotes
}