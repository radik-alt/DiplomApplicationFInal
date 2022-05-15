package com.example.diplomapplication.ui.NotesUI.createPIN

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentPasswordBinding
import com.example.diplomapplication.room.notes.Notes
import com.example.diplomapplication.ui.NotesUI.SharedViewModelNotes
import com.hanks.passcodeview.PasscodeView


class PasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    private val sharedNotesModel : SharedViewModelNotes by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)

        // взятие из БД пароля
        val note: Notes = sharedNotesModel.getNotes().value!!
        val pass = note.password.toString()

        // проверка пароля (ограничения на кол-во символов, и сам пароль, который мы ввели)
        binding.validPasswordNotes.setPasscodeLength(4).setLocalPasscode(pass).setListener(object : PasscodeView.PasscodeViewListener{
            override fun onFail() {

            }

            override fun onSuccess(number: String?) {
                Navigation.findNavController(view!!).navigate(R.id.editNotesFragment)
            }

        })

        return binding.root
    }

}