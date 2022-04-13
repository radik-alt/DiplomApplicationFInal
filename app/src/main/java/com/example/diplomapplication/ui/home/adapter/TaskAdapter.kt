package com.example.diplomapplication.ui.home.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.R
import com.example.diplomapplication.room.task.Utils.Priority
import com.example.diplomapplication.ui.home.Interface.OnDeleteTask
import com.example.diplomapplication.ui.home.Interface.ToDoClickTask
import java.util.*

class TaskAdapter(private var context: Context, private var currentDate: Date,
                  private var list:List<Task>, private var toDoClickTask: ToDoClickTask)
    : RecyclerView.Adapter<TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.block_task,
            parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        var colorState = ColorStateList(arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(android.R.attr.state_enabled)
        ), intArrayOf(
            priorityColor(list[position].priorityTask),
            Color.WHITE
        ))

        val dateCreateTask = list[position].dateCreatedTask
        holder.nameTask.text = list[position].nameTask
        holder.nameTask.setTextColor(Color.WHITE)
        holder.dateTask.text = convertDate(dateCreateTask)
        colorState.also { holder.cheackTask.buttonTintList = it }

        holder.dateTask.setOnClickListener {
            toDoClickTask.getTask(list[position], 1)
        }

        holder.cheackTask.setOnClickListener {
            toDoClickTask.getTask(list[position], 0)
        }

        holder.itemView.setOnClickListener{
            toDoClickTask.getTask(list[position], 1)
        }


    }

    private fun convertDate(date: Date?): String {
        if (date != null) {
            return DateFormat.format("MMMM dd yyyy", date.time).toString()
        }
        return "Нет данных!"
    }

    private fun priorityColor(priority: Priority): Int {
        when(priority){
            Priority.Max -> return R.color.max_priority
            Priority.Middle -> return R.color.middle_priority
            Priority.Low -> return R.color.low_priority
            else -> return Color.WHITE
        }
    }


    override fun getItemCount(): Int {
        return list.size
    }



}