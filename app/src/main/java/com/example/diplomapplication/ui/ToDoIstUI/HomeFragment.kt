package com.example.diplomapplication.ui.ToDoIstUI

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplomapplication.R
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.databinding.FragmentHomeBinding
import com.example.diplomapplication.room.task.TaskViewModel
import com.example.diplomapplication.room.task.Utils.Priority
import com.example.diplomapplication.ui.accountUI.RegistrationActivity
import com.example.diplomapplication.ui.ToDoIstUI.Interface.ToDoClickTask
import com.example.diplomapplication.ui.ToDoIstUI.adapter.TaskAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModelTask: TaskViewModel
    // загугли viewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var currentDate = Calendar.getInstance()
    private lateinit var uid: String
    private lateinit var auth: FirebaseAuth
    private lateinit var preferences: SharedPreferences

    companion object{
        private val KEY_SHARED = "Shared"
        var changeDate: ArrayList<Task> = ArrayList<Task>()
        var fullListDate : ArrayList<Task> = ArrayList<Task>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelTask = ViewModelProvider(this).get(TaskViewModel::class.java)
        // загугли
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        auth = FirebaseAuth.getInstance()
        uid = auth.uid.toString()
        preferences = requireContext().getSharedPreferences(KEY_SHARED ,Context.MODE_PRIVATE)

        viewModelTask.getTask().observe(viewLifecycleOwner){ taskList ->
            fullListDate.clear()
            fullListDate.addAll(taskList)
            changeList(taskList)
            setAdapter()
        }

        // для установление даты задачи
        binding.dateChoosen.setOnClickListener {
            val yearC = currentDate.get(Calendar.YEAR)
            val monthC = currentDate.get(Calendar.MONTH)
            val dayC = currentDate.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                currentDate.set(year, monthOfYear, dayOfMonth)

                // сортировка
                changeList(fullListDate)

                // отображение даты
                if (Calendar.getInstance().time.date.equals(dayOfMonth)){
                    binding.nowDateTask.text = "Сегодня"
                } else {
                    binding.nowDateTask.text = convertDate(currentDate.time)
                }

            }, yearC, monthC, dayC)
            dpd.show()
        }

        // кнопка добавление задачи
        binding.addTask.setOnClickListener {
            sharedViewModel.setIsEdit(false)
            bottomSheet()
        }

        return binding.root
    }

    private fun convertDate(date: Date?): String {
        if (date != null) {
            return DateFormat.format("MMMM dd yyyy", date.time).toString()
        }
        return "Нет данных!"
    }

    private fun setAdapter () {
        // как должны располаться item в списке
        binding.recycleTask.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        // реализация интрефейса
            val adapter = TaskAdapter(requireContext(), uid, changeDate, object : ToDoClickTask{
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
        binding.recycleTask.adapter = adapter
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recycleTask)
    }

    // сортировка
    private fun changeList(taskList: List<Task>?){
        changeDate.clear()
        val tempList: ArrayList<Task> = ArrayList()

        // добавляем по дате
        for (i in taskList!!){
            val dateNow = currentDate.time.date.compareTo(i.dateCreatedTask!!.date)
            if (dateNow == 0){
                changeDate.add(i)
            }
        }

        // доавбляем по приоритету
        for (i in changeDate){
            if (i.priorityTask == Priority.Max && i.uID == uid){
                tempList.add(i)
            }
        }

        // доавбляем по приоритету
        for (i in changeDate){
            if (i.priorityTask == Priority.Middle && i.uID == uid){
                tempList.add(i)
            }
        }

        // доавбляем по приоритету
        for (i in changeDate){
            if (i.priorityTask == Priority.Low && i.uID == uid){
                tempList.add(i)
            }
        }

        // доавбляем по приоритету
        for (i in changeDate){
            if (i.priorityTask == Priority.NoPriority && i.uID == uid){
                tempList.add(i)
            }
        }

        changeDate.clear()
        changeDate.addAll(tempList)

        // устанавливаем адаптер
        setAdapter()
    }

    // swipe и переход на таймер
    val simpleCallback : ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        // здесь swipe
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Navigation.findNavController(view!!).navigate(R.id.nav_slideshow)
        }

    }

    // октрытие bottom окна для доавбление задачи
    private fun bottomSheet(){
        val bottomSheetFragment  = BottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
    }


    // заугли (это меню в ActionBar)
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.achievements, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // загугли (это взайимодействие с menu)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.achivement){
            Navigation.findNavController(requireView()).navigate(R.id.achievementsFragment)
        } else if (item.itemId == R.id.settings){
            val bottomsheet = BottomSheetDialog(requireContext())
            bottomsheet.setContentView(R.layout.bottomsheet_setting)

            val logout = bottomsheet.findViewById<Button>(R.id.logout)
            val changeThemes = bottomsheet.findViewById<Switch>(R.id.themeNightOrDay)
            logout!!.setOnClickListener {
                auth.signOut()
                requireContext().startActivity(Intent(requireContext(), RegistrationActivity::class.java))
            }

            changeThemes!!.isChecked = preferences.getBoolean(KEY_SHARED, false)
            changeThemes!!.setOnCheckedChangeListener { compoundButton, cheacked ->

                if (cheacked){
                    preferences.edit().putBoolean(KEY_SHARED, cheacked).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    preferences.edit().putBoolean(KEY_SHARED, cheacked).apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }

            bottomsheet.show()
        }
        return super.onOptionsItemSelected(item)
    }

}