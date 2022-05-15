package com.example.diplomapplication.ui.ToDoIstUI.adapterAchievment

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.diplomapplication.R
import com.example.diplomapplication.room.achivment.Achievment

class AdapterAchievment(private val context: Context, private var list: ArrayList<Achievment>)
    : RecyclerView.Adapter<AchivmentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchivmentViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.achiviement_layout, parent, false)
        return AchivmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AchivmentViewHolder, position: Int) {

        if (list[position].doing){
            val idBg = context.resources.getColor(R.color.textNotes)
            holder.bgAchievment.setBackgroundColor(idBg)
        }

        var idImage = R.drawable.ic_launcher_foreground
        if (position == 0){
            idImage = R.drawable.graph
        } else if (position == 1) {
            idImage = R.drawable.goal
        } else if (position == 2){
            idImage = R.drawable.climbing
        }

        Log.d("ResultSize", "${list.size}")

        Glide
            .with(context)
            .load(idImage)
            .into(holder.imageAchievement)

        holder.nameAchievment.text = list[position].nameAchievment
        holder.descAchievment.text = list[position].descAChievement
    }

    override fun getItemCount(): Int = list.size
}