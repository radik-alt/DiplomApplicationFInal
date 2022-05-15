package com.example.diplomapplication.ui.ToDoIstUI.adapterAchievment

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R

class AchivmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val nameAchievment = itemView.findViewById<TextView>(R.id.nameAchievment)
    val descAchievment = itemView.findViewById<TextView>(R.id.descriptionAchievment)
    val bgAchievment = itemView.findViewById<LinearLayout>(R.id.bgAchievment)
    val imageAchievement = itemView.findViewById<ImageView>(R.id.imageAchievement)
}