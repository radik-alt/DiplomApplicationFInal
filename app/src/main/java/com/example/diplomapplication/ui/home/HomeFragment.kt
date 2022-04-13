package com.example.diplomapplication.ui.home

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomapplication.R
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.databinding.FragmentHomeBinding
import com.example.diplomapplication.room.task.TaskViewModel
import com.example.diplomapplication.ui.home.Interface.OnDeleteTask
import com.example.diplomapplication.ui.home.Interface.ToDoClickTask
import com.example.diplomapplication.ui.home.adapter.TaskAdapter
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelTask: TaskViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var currentDate = Calendar.getInstance()
    private var c = Calendar.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelTask = ViewModelProvider(this).get(TaskViewModel::class.java)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        viewModelTask.getTask().observe(viewLifecycleOwner){ taskList ->

            binding.recycleTask.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            binding.recycleTask.adapter = TaskAdapter(requireContext(), currentDate.time, taskList, object : ToDoClickTask{
                override fun getTask(task: Task, choose: Int) {
                    if (choose == 1){
                        sharedViewModel.selecteItem(task)
                        sharedViewModel.setIsEdit(true)
                        bottomSheet()
                    } else if (choose == 0){
                        viewModelTask.deleteTask(task.idTask!!)
                    }

                }

            })

        }

        binding.prevDate.setOnClickListener {  }

        binding.nextDate.setOnClickListener {  }

        binding.callDataAlert.setOnClickListener {
            val yearC = c.get(Calendar.YEAR)
            val monthC = c.get(Calendar.MONTH)
            val dayC = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                currentDate.set(year, monthOfYear+1, dayOfMonth)
                Log.d("MyDateSelecte", "${currentDate.time}" )
            }, yearC, monthC, dayC)
            dpd.show()
        }


        binding.addTask.setOnClickListener {
            sharedViewModel.setIsEdit(false)
            bottomSheet()
        }

        return binding.root
    }


    private fun bottomSheet(){
        val bottomSheetFragment  = BottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.achievements, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.achivement){
            Navigation.findNavController(requireView()).navigate(R.id.achievementsFragment)
        }
        return super.onOptionsItemSelected(item)
    }

}