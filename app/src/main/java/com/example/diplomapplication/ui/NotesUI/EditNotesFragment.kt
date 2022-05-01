package com.example.diplomapplication.ui.NotesUI

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentEditNotesBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.room.notes.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class EditNotesFragment : Fragment() {

    private lateinit var viewModel: NotesViewModel
    private lateinit var binding: FragmentEditNotesBinding
    // для передачи модели заметки использовали viewModel
    private val sharedNotesModel : SharedViewModelNotes by activityViewModels()
    private lateinit var notes: Notes

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditNotesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        setHasOptionsMenu(true)

        // заполнение уже существующей модели данными
        sharedNotesModel.notes.observe(viewLifecycleOwner){
            notes = it
            binding.title.setText(it.title)
            binding.description.setText(it.description)
            binding.notes.setText(it.notes)
        }

        // сохранение заметки
        binding.saveNotes.setOnClickListener {
            changeNotes(it)
        }

        // открытие поля для ввода пароля по клику на checkbox
        binding.clickPrivatePassword.setOnClickListener {
            val showPass = binding.passwordNotes
            if (showPass.visibility == View.GONE)
                showPass.visibility = View.VISIBLE
            else
                showPass.visibility = View.GONE
        }

        return binding.root
    }

    // изменение заметки
    private fun changeNotes(it:View?){

        val title = binding.title.text.toString()
        val description = binding.description.text.toString()
        val textNotes = binding.notes.text.toString()

        // изменения существующей модели
        notes.title = title
        notes.description = description
        notes.notes = textNotes

        // update в БД
        viewModel.updateNotes(notes)

        Toast.makeText(requireContext(), "Запись изменена!", Toast.LENGTH_SHORT).show()
        // переход назад
        Navigation.findNavController(requireView()).navigate(R.id.nav_gallery)
    }

    // корзина на Actionbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // клик по корзине выводит BottomSheetDialog, по нажатию на yes удаляем запись
    // по нажатию на no скрывает BottomSheetDialog
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteNotes){
            val bottomsheet = BottomSheetDialog(requireContext())
            bottomsheet.setContentView(R.layout.bottom_permission)
            bottomsheet.show()

            val deleteYes = bottomsheet.findViewById<Button>(R.id.delete_yes)
            val deleteNo = bottomsheet.findViewById<Button>(R.id.delete_no)

            deleteYes?.setOnClickListener {
                viewModel.deleteNotes(notes.id!!)
                Toast.makeText(requireContext(), "Запись удалена!", Toast.LENGTH_SHORT).show()
                Navigation.findNavController(requireView()).navigate(R.id.nav_gallery)
                bottomsheet.cancel()
            }

            deleteNo?.setOnClickListener {
                bottomsheet.cancel()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}