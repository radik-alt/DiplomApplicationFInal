package com.example.diplomapplication.ui.ToDoIstUI.adapter

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
import com.example.diplomapplication.ui.ToDoIstUI.Interface.ToDoClickTask
import java.util.*

class TaskAdapter(private var context: Context, private var uid: String,
                  private var list:List<Task>, private var toDoClickTask: ToDoClickTask)
    : RecyclerView.Adapter<TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.block_task,
            parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        // берем значение Date сегодняшнее и которое в БД
        val dateCreateTask = list[position].dateCreatedTask
        val dateNow = Calendar.getInstance().time
        holder.nameTask.text = list[position].nameTask

        // сравниваем день и месяц
        val selectedDate = dateCreateTask!!.date.equals(dateNow.date)
        val selectedMouth = dateCreateTask.month.equals(dateNow.month)

        // если день и месяц совпдаает
        if (selectedDate && selectedMouth){
            holder.dateTask.text = "Сегодня"
            holder.nameTask.setTextColor(Color.WHITE)
            Log.d("ResultDate", "${list[position]} $selectedDate ${selectedMouth}")
        } else{
            holder.dateTask.text = convertDate(dateCreateTask)
        }


        // это нужно для установки цвета на кружок слева в задаче (просто запомни)
        val colorStateListMax = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), // unchecked
                intArrayOf(android.R.attr.state_checked) // checked
            ), intArrayOf(
                context.resources.getColor(R.color.max_priority), // unchecked color
                context.resources.getColor(R.color.max_priority) // checked color
            )
        )

        // это нужно для установки цвета на кружок слева в задаче (просто запомни)
        val colorStateListMiddle = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), // unchecked
                intArrayOf(android.R.attr.state_checked) // checked
            ), intArrayOf(
                context.resources.getColor(R.color.middle_priority),// unchecked color
                context.resources.getColor(R.color.middle_priority) // checked color
            )
        )

        // это нужно для установки цвета на кружок слева в задаче (просто запомни)
        val colorStateListLow = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked), // unchecked
                intArrayOf(android.R.attr.state_checked) // checked
            ), intArrayOf(
                context.resources.getColor(R.color.low_priority),// unchecked color
                context.resources.getColor(R.color.low_priority) // checked color
            )
        )

        // установка приритета задачи
        val priorityTask = list[position].priorityTask
        if (priorityTask == Priority.Max){
            holder.cheackTask.buttonTintList = colorStateListMax
        } else if (priorityTask == Priority.Middle) {
            holder.cheackTask.buttonTintList = colorStateListMiddle
        } else if (priorityTask == Priority.Low){
            holder.cheackTask.buttonTintList = colorStateListLow
        }

        // клике по дате (редактирование)
        holder.dateTask.setOnClickListener {
            toDoClickTask.getTask(list[position], 1)
        }

        // удаление задачи
        holder.cheackTask.setOnClickListener {
            toDoClickTask.getTask(list[position], 0)
        }

        // клик просто в обьекту (редактирование)
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

    override fun getItemCount(): Int {
        return list.size
    }



}