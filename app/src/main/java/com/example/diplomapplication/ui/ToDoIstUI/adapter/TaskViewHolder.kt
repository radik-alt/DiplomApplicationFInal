package com.example.diplomapplication.ui.ToDoIstUI.adapter

import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.google.android.material.chip.Chip

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val nameTask: TextView = itemView.findViewById<EditText>(R.id.nameTask)
    val dateTask: Chip = itemView.findViewById<Chip>(R.id.dateTask)
    val cheackTask = itemView.findViewById<RadioButton>(R.id.checkTask)

    override fun onClick(view: View?) {
        Log.d("viewGetId", view!!.id.toString())
    }


}