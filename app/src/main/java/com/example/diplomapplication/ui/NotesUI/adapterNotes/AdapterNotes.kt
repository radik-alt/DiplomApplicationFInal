package com.example.diplomapplication.ui.NotesUI.adapterNotes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.ItemNotesBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.ui.NotesUI.Interface.onClikcNotes

class AdapterNotes(val context:Context, val notes: List<Notes>, private var onClickNotes: onClikcNotes) : RecyclerView.Adapter<ViewHolderNotes>(){
    // в конструкторе передаем context (это абстрактный класс,
    // который получает понимание, какой активити наполнять)
    // и список с model (его мы адаптируем под нужный нам дизайн)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderNotes {
        // адапетр состоит из 2-ух классов, здесьмы передаем в класс ViewHolderNotes view,
        // которое мы используем как шаблон для наполнения контента
        val view = ItemNotesBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolderNotes(view)
    }

    override fun onBindViewHolder(holder: ViewHolderNotes, position: Int) {
        // holder мы полуачем из ViewHolderNotes view элементы,
        // которые мы здесь преобразуем,
        // а position это индекс List<Notes>
        val data = notes[position]

        // меняем заголовок
        holder.titleNotes.text = data.title

        // смотрим есть ли ключ у данной заметки
        // если есть, то ставим пустую строку, иначе данные из model (текст заметки и дата)
        val notesText = holder.textNotes
        val dateNotes = holder.dateNotes
        if (notes[position].IsPassword) {
            notesText.text = ""
            dateNotes.text = ""
        } else {
            notesText.text = data.notes
            dateNotes.text = data.date
        }


        // отображение приоритета и берем цвет из ресурсов (res -> color (там цвета))
        val bg = holder.bgNotes
        when (data.priority){
            "Max" -> {
                bg.setBackgroundResource(R.color.max_priority)
            }
            "Middle" -> {
                bg.setBackgroundResource(R.color.middle_priority)
            }
            "Low" -> {
                bg.setBackgroundResource(R.color.low_priority)
            }
        }


        // клики по заметке и переход на новый экран редактирования или сначала ввода пароля
        holder.itemView.setOnClickListener {
            if (notes[position].IsPassword){
                Navigation.findNavController(it).navigate(R.id.action_nav_gallery_to_passwordFragment)
            } else {
                Navigation.findNavController(it).navigate(R.id.editNotesFragment)
            }
            onClickNotes.getSelectNotes(notes[position])

        }

    }

    // размер листа, который мы передали
    override fun getItemCount(): Int = notes.size

}