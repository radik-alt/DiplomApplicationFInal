package com.example.diplomapplication.ui.TimerUI.adapterTimer

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R

class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val timerWork = itemView.findViewById<TextView>(R.id.timeWork)
    val timerRelax = itemView.findViewById<TextView>(R.id.timeRelax)
    val quantityTimer = itemView.findViewById<TextView>(R.id.quantityTime)
    val dateTIme = itemView.findViewById<TextView>(R.id.DateTime)

}