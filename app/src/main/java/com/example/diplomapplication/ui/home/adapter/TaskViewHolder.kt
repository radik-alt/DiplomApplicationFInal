package com.example.diplomapplication.ui.home.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nameTask = itemView.findViewById<TextView>(R.id.nameTask)
    val descriptionTask = itemView.findViewById<TextView>(R.id.descriptionTask)
    val time = itemView.findViewById<TextView>(R.id.dateTask)
    val checkeTask = itemView.findViewById<CheckBox>(R.id.checkTask)
}