package com.example.diplomapplication.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentGalleryBinding
import com.example.diplomapplication.room.notes.NotesViewModel
import com.example.diplomapplication.ui.gallery.adapterNotes.AdapterNotes

class GalleryFragment : Fragment() {

    private lateinit var _binding: FragmentGalleryBinding
    private val binding get() = _binding
    private lateinit var notesViewModel: NotesViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notesViewModel = ViewModelProvider(this).get(NotesViewModel::class.java)
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        binding.addNotes.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.appendNotesFragment)
        }

        binding.findAllNotes.setOnClickListener {
            notesViewModel.getNotes().observe(viewLifecycleOwner) { noteList ->

                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), noteList)
            }
        }

        binding.findMaxNotes.setOnClickListener {
            notesViewModel.getHighNotes().observe(viewLifecycleOwner) { noteList ->

                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), noteList)
            }
        }

        binding.findMiddleNotes.setOnClickListener {
            notesViewModel.getMiddleNotes().observe(viewLifecycleOwner) { noteList ->

                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), noteList)
            }
        }

        binding.findLowNotes.setOnClickListener {
            notesViewModel.getLowNotes().observe(viewLifecycleOwner) { noteList ->

                binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
                binding.recycleNotes.adapter = AdapterNotes(requireContext(), noteList)
            }
        }

        notesViewModel.getNotes().observe(viewLifecycleOwner) { noteList ->

            binding.recycleNotes.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.recycleNotes.adapter = AdapterNotes(requireContext(), noteList)
        }

        return binding.root
    }

}