package com.example.diplomapplication.ui.NotesUI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentGalleryBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.room.notes.NotesViewModel
import com.example.diplomapplication.ui.NotesUI.Interface.onClikcNotes
import com.example.diplomapplication.ui.NotesUI.adapterNotes.AdapterNotes
import com.google.firebase.auth.FirebaseAuth

class GalleryFragment : Fragment() {

    private lateinit var _binding: FragmentGalleryBinding
    private val binding get() = _binding
    private lateinit var notesViewModel: NotesViewModel
    private val sharedNotesModel : SharedViewModelNotes by activityViewModels()
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // подключение viewModel с базой данных заметок
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        // проверка авторриазции пользователя firebase
        // и получение его UID (уникальный индификатор)
        auth = FirebaseAuth.getInstance()
        uid = auth.uid.toString()


        // кнопка перехода в новый фрагмент создания заметки
        binding.addNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.appendNotesFragment)
        }

        // получение все заметок в БД заметок
        // и передача в адапетр, адапетр их выводит на экран (в адаптаре описано как работает адаптер)
        binding.findAllNotes.setOnClickListener {
            notesViewModel.getNotes().observe(viewLifecycleOwner) { noteList ->

                // получаем новый список через функцию changeList
                // и передаем его в адаптер
                val newList = changeList(noteList)
                // layoutmanager нужен, чтоб определить как его отображать
                // здесь отображается через гриды в 2 колонки
                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                // а это сам адаптер
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), newList, object : onClikcNotes{
                    // здесь
                    override fun getSelectNotes(notes: Notes) {
                        // при клике на выбранную заметку сохраняем ее в sharedNotesModel
                        // и при переходе используем данную модель
                        sharedNotesModel.setNotes(notes)
                    }
                })
            }
        }

        // выводит из бд результаты по приоритету работы
        binding.findMaxNotes.setOnClickListener {
            notesViewModel.getHighNotes().observe(viewLifecycleOwner) { noteList ->

                val newList = changeList(noteList)
                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), newList, object : onClikcNotes{
                    override fun getSelectNotes(notes: Notes) {
                        // при клике на выбранную заметку сохраняем ее в sharedNotesModel
                        // и при переходе используем данную модель
                        sharedNotesModel.setNotes(notes)
                    }
                })
            }
        }

        // выводит из бд результаты по приоритету учебы
        binding.findMiddleNotes.setOnClickListener {
            notesViewModel.getMiddleNotes().observe(viewLifecycleOwner) { noteList ->

                val newList = changeList(noteList)
                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), newList, object : onClikcNotes{
                    override fun getSelectNotes(notes: Notes) {
                        // при клике на выбранную заметку сохраняем ее в sharedNotesModel
                        // и при переходе используем данную модель
                        sharedNotesModel.setNotes(notes)

                    }
                })
            }
        }

        // выводит из бд результаты по приоритету другие
        binding.findLowNotes.setOnClickListener {
            notesViewModel.getLowNotes().observe(viewLifecycleOwner) { noteList ->

                val newList = changeList(noteList)
                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), newList, object : onClikcNotes{
                    override fun getSelectNotes(notes: Notes) {
                        // при клике на выбранную заметку сохраняем ее в sharedNotesModel
                        // и при переходе используем данную модель
                        sharedNotesModel.setNotes(notes)
                    }
                })
            }
        }

        // выводит из бд результаты все заметки
        notesViewModel.getNotes().observe(viewLifecycleOwner) { noteList ->

            val newList = changeList(noteList)
            binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recycleNotes.adapter = AdapterNotes(requireContext(), newList, object : onClikcNotes{
                override fun getSelectNotes(notes: Notes) {
                    // при клике на выбранную заметку сохраняем ее в sharedNotesModel
                    // и при переходе используем данную модель
                    sharedNotesModel.setNotes(notes)
                }
            })
        }

        return binding.root
    }

    // сортирует по UID заметки
    private fun changeList(list: List<Notes>): ArrayList<Notes>{
        val newList = ArrayList<Notes>()

        for (i in list){
            if (i.uId == uid)
                newList.add(i)
        }

        return newList
    }

}