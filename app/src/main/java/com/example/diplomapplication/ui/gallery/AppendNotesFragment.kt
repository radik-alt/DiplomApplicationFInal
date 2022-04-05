package com.example.diplomapplication.ui.gallery

import android.os.Bundle
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppendNotesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)

        binding.saveNotes.setOnClickListener {
            createNotes(it)
        }

        return binding.root
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
            priority = "Max"
        )

        viewModel.addNotes(notes)

        Toast.makeText(requireContext(), "Запись добавлена!", Toast.LENGTH_SHORT).show()

        Navigation.findNavController(it!!).navigate(R.id.nav_gallery)

    }

}