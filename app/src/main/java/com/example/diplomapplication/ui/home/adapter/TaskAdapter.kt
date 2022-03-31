package com.example.diplomapplication.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.Model.Task
import com.example.diplomapplication.R

class TaskAdapter(private var context: Context, private var list:ArrayList<Task>) : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.block_task,
            parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.nameTask.text = list[position].name
        holder.descriptionTask.text = list[position].description
        holder.time.text = list[position].date
    }

    override fun getItemCount(): Int {
        return list.size
    }

}