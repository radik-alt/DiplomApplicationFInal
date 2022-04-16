package com.example.diplomapplication.ui.gallery

import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.format.DateFormat
import android.util.Log
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
import java.util.*


class AppendNotesFragment : Fragment() {

    private lateinit var binding: FragmentAppendNotesBinding
    private lateinit var viewModel: NotesViewModel
    private var notesPriority = "No Priority"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppendNotesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.saveNotes.setOnClickListener {
            createNotes(it)
        }

        binding.clickPrivatePassword.setOnClickListener {
            val showPass = binding.passwordNotes
            if (showPass.visibility == View.GONE)
                showPass.visibility = View.VISIBLE
            else
                showPass.visibility = View.GONE
        }

        binding.maxNotes.setOnClickListener {
            notesPriority = "Max"
        }

        binding.middleNotes.setOnClickListener {
            notesPriority = "Middle"
        }

        binding.lowNotes.setOnClickListener {
            notesPriority = "Low"
        }

        binding.styleBoldText.setOnClickListener {
            changeText()
        }

        binding.styleItalicText.setOnClickListener {

        }

        return binding.root
    }

    private fun changeText() {
        val textNotes = binding.notes
        val startSelection= textNotes.selectionStart
        val endSelection= textNotes.selectionEnd

        val selectedText = textNotes.text.toString().substring(startSelection, endSelection)
        try {
            val strTemp = SpannableStringBuilder(selectedText)
            strTemp.setSpan(android.text.style.StyleSpan(android.graphics.Typeface.BOLD), startSelection, endSelection, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textNotes.setSelection(startSelection, endSelection)
            textNotes.text = strTemp
        } catch (e: Exception){
            Toast.makeText(requireContext(), "Выделите полностью слово", Toast.LENGTH_SHORT).show()
        }

        Log.d("ChoosenTextNotes", "$startSelection $endSelection $selectedText")
    }

    private fun createNotes(it :View?) {
        var title = binding.title.text.toString()
        var description = binding.description.text.toString()
        var textNotes = binding.notes.text.toString()

        val date = Date()
        val notesDate = DateFormat.format("MMMM dd yyyy", date.time).toString()
        Log.d("MyTimeData", notesDate)

        var notes = Notes(
            null,
            title = title,
            description = description,
            notes = textNotes,
            date = notesDate,
            priority = notesPriority
        )

        viewModel.addNotes(notes)

        Toast.makeText(requireContext(), "Запись добавлена!", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.nav_gallery)

    }



}