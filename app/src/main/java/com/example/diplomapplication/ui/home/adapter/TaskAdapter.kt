package com.example.diplomapplication.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.R
import com.example.diplomapplication.ui.home.Interface.ToDoClickTask

class TaskAdapter(private var context: Context, private var list:List<Task>, private var toDoClickTask: ToDoClickTask)
    : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.block_task,
            parent, false)

        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.nameTask.text = list[position].nameTask

        holder.itemView.setOnClickListener {
            toDoClickTask.getTask(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}