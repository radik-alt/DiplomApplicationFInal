package com.example.diplomapplication.ui.NotesUI

import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentAppendNotesBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.room.notes.NotesViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class AppendNotesFragment : Fragment() {

    private lateinit var binding: FragmentAppendNotesBinding
    private lateinit var viewModel: NotesViewModel
    private var checked : Boolean = false
    private var notesPriority = "No Priority"
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentAppendNotesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        // проверка авторриазции пользователя firebase
        // и получение его UID (уникальный индификатор)
        auth = FirebaseAuth.getInstance()
        uid = auth.uid.toString()


        // сохранение изменений
        binding.saveNotes.setOnClickListener {
            createNotes(it)
        }

        // открытие поля для ввода пароля по клику на checkbox
        binding.clickPrivatePassword.setOnClickListener {
            val showPass = binding.passwordNotes
            if (showPass.visibility == View.GONE){
                showPass.visibility = View.VISIBLE
                checked = true
            }
            else {
                showPass.visibility = View.GONE
                checked = false
            }
        }

        // установка приоритетов
        binding.maxNotes.setOnClickListener {
            notesPriority = "Max"
        }

        binding.middleNotes.setOnClickListener {
            notesPriority = "Middle"
        }

        binding.lowNotes.setOnClickListener {
            notesPriority = "Low"
        }


        return binding.root
    }


    // создание заметки
    private fun createNotes(it :View?) {
        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val textNotes = binding.notes.text.toString()
        val date = Date()
        val notesDate = DateFormat.format("MMMM dd yyyy", date.time).toString()

        // true, если мы ставим пароль и создаем новую заметку в БД
        // false, если мы не ставим пароль и создаем новую заметку
        if (checked){
            val password = binding.passwordNotes.text.toString()
            if (validPassword(password)) {
                val notes = Notes(
                    null,
                    title = title,
                    description = description,
                    notes = textNotes,
                    date = notesDate,
                    priority = notesPriority,
                    IsPassword = checked,
                    password = password,
                    uId = uid
                )

                // добавление в БД
                viewModel.addNotes(notes)

                //Переход на экран со всеми заметками
                Toast.makeText(requireContext(), "Запись добавлена!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(it!!).navigate(R.id.nav_gallery)
            } else {
                Toast.makeText(requireContext(), "Введите корректный пароль!", Toast.LENGTH_SHORT).show()
            }
        } else {

            val notes = Notes(
                null,
                title = title,
                description = description,
                notes = textNotes,
                date = notesDate,
                priority = notesPriority,
                IsPassword = checked,
                password = null,
                uid
            )

            // добавление в БД и переход на экран со всеми заметками
            viewModel.addNotes(notes)
            Toast.makeText(requireContext(), "Запись добавлена!", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it!!).navigate(R.id.nav_gallery)
        }

    }

    private fun validPassword(password:String): Boolean {
        if (password.length < 4){
            return false
        }
        return true
    }


}