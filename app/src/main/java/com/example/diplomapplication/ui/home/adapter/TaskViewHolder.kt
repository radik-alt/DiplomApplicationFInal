package com.example.diplomapplication.ui.home.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.google.android.material.chip.Chip

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nameTask: TextView = itemView.findViewById<EditText>(R.id.nameTask)
    val dateTask: Chip = itemView.findViewById<Chip>(R.id.dateTask)
}