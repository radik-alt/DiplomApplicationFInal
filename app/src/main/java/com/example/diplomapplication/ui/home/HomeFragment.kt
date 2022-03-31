package com.example.diplomapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.Model.Task
import com.example.diplomapplication.R
import com.example.diplomapplication.databinding.FragmentHomeBinding
import com.example.diplomapplication.ui.home.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var fullTask: ArrayList<Task> = ArrayList()

    companion object{
        private lateinit var taskAdapter: TaskAdapter
        private var taskList: ArrayList<Task> = ArrayList()

        private fun updateListTask(){
            taskAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adapterSpinner()
        adapterTask()

        _binding?.createTask?.setOnClickListener {
            var task = binding.nameTask.text.toString()
            var description = binding.descriptionTask.text.toString()

            taskList.add(Task(0, task, description, 1,"2020-10-12"))
            updateListTask()
        }

        homeViewModel.text.observe(viewLifecycleOwner) {

        }

        return root
    }



    private fun adapterTask(){
        taskAdapter = TaskAdapter(requireContext(), taskList)
        var lmanager: RecyclerView.LayoutManager = LinearLayoutManager(requireContext(),
            RecyclerView.HORIZONTAL, false)

        var recycle = binding.recycleTask
        recycle.layoutManager = lmanager
        recycle.adapter = taskAdapter
    }

    private fun adapterSpinner(){
        var adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.Priority,
            android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.priorityTask.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}