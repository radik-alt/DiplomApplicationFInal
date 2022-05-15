package com.example.diplomapplication.ui.ToDoIstUI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomapplication.databinding.FragmentAchievementsBinding
import com.example.diplomapplication.room.achivment.Achievment
import com.example.diplomapplication.room.achivment.AchievmentViewModel
import com.example.diplomapplication.room.notes.NotesViewModel
import com.example.diplomapplication.room.task.TaskViewModel
import com.example.diplomapplication.room.timerPomadoro.TimerViewModel
import com.example.diplomapplication.ui.ToDoIstUI.adapterAchievment.AdapterAchievment
import com.google.firebase.auth.FirebaseAuth

class AchievementsFragment : Fragment() {

    private lateinit var binding: FragmentAchievementsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var uID:String
    private val achievmentViewModel : AchievmentViewModel by activityViewModels()
    private val notesViewModel: NotesViewModel by activityViewModels()
    private val taskViewModel : TaskViewModel by activityViewModels()
    private val timerViewModel : TimerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAchievementsBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        uID = auth.uid.toString()

        val countNotes = notesViewModel.getCountNotes()
        val countTask = taskViewModel.getCountTask()
        val countTimer = timerViewModel.getCountTimer()

        // проверка если уже есть 3 задачи 3 заметки
        // мы обновляем ему значение doing в модели на true (потом отбражает по другому данное достижение)
        if (countNotes >= 3 && countTask >= 3) {
            achievmentViewModel.updateAchievment(7)
        }

        // проверка если уже есть 5 задачи 5 заметок
        if (countNotes >= 5 && countTask >= 5){
            achievmentViewModel.updateAchievment(8)
        }

        // проверка если уже есть 10 задач 10 заметки и 3 раза запущен таймер
        if (countNotes >= 10 && countTask >= 10 && countTimer >= 3){
            achievmentViewModel.updateAchievment(9)
        }

        // вывод списка, адапетр
        achievmentViewModel.getAchiemvnet().observe(viewLifecycleOwner){

            val recycle = binding.recycleAchievement
            recycle.adapter = AdapterAchievment(requireContext(), it as ArrayList<Achievment>)
            recycle.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
        }

        return binding.root
    }


    // это нужно было для загрузки в БД данных о достижении, можешь больше не использовать
    private fun changeList() {
        val nameList: ArrayList<String> = ArrayList()
        nameList.add("Начальный уровень")
        nameList.add("Средний уровень")
        nameList.add("Уровень мастер")

        val desList : ArrayList<String> = ArrayList()
        desList.add("Завершите 3 задачи, создайте 3 заметки")
        desList.add("Завершите 5 задач и создайте 5 заметок")
        desList.add("Завершите 10 задач, запустите 3 раз таймер и создайте 10 заметок")

        for (i in 0..2){
            val achievment = Achievment(null, nameList[i], desList[i], false, uID)
            achievmentViewModel.addAchievment(achievment)
        }

    }

}