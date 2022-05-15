package com.example.diplomapplication.ui.TimerUI.adapterTimer

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.example.diplomapplication.room.timerPomadoro.TimerModel
import java.util.*

class adapterTimer(private var context: Context, private var list: List<TimerModel>) : RecyclerView.Adapter<TimerViewHolder>() {

    // здесь все как в других адаптерах, ничего нового нет!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.timer_adapter_recylce, parent, false)
        return TimerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {

        holder.timerWork.text = "${list[position].timerWork} минут"
        holder.timerRelax.text = "${list[position].timerRelax} минут"
        holder.quantityTimer.text = list[position].quantityTimer.toString()
        holder.dateTIme.text = convertDate(list[position].dateTime)

    }

    private fun convertDate(date: Date?): String {
        if (date != null) {
            return DateFormat.format("MMMM dd yyyy", date.time).toString()
        }
        return "Нет данных!"
    }

    override fun getItemCount(): Int = list.size
}