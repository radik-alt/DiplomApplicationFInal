package com.example.diplomapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.databinding.FragmentHomeBinding
import com.example.diplomapplication.room.task.TaskViewModel
import com.example.diplomapplication.ui.home.Interface.ToDoClickTask
import com.example.diplomapplication.ui.home.adapter.TaskAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelTask: TaskViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelTask = ViewModelProvider(this).get(TaskViewModel::class.java)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModelTask.getTask().observe(viewLifecycleOwner){ taskList ->
            binding.recycleTask.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            binding.recycleTask.adapter = TaskAdapter(requireContext(), taskList, object : ToDoClickTask{
                override fun getTask(task: Task) {
                    sharedViewModel.selecteItem(task)
                    Log.d("MyTaskLog", sharedViewModel.getItem().value.toString())
                    bottomSheet()
                }
            })
        }

        binding.addTask.setOnClickListener {
            bottomSheet()
        }

        return binding.root
    }


    private fun bottomSheet(){
        val bottomSheetFragment  = BottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }


}