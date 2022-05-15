package com.example.diplomapplication.ui.ToDoIstUI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.diplomapplication.R
import com.example.diplomapplication.room.task.Utils.Priority
import com.example.diplomapplication.room.task.Task
import com.example.diplomapplication.room.task.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import java.util.*


class BottomSheetFragment() : BottomSheetDialogFragment() {

    private lateinit var clickCalendar: ImageButton
    private lateinit var clickPriority: ImageButton
    private lateinit var nameTask: EditText
    private lateinit var calendar: CalendarView
    private lateinit var saveTask: ImageButton
    private lateinit var groupPriority: RadioGroup
    private lateinit var linerCalendar: LinearLayout
    private lateinit var viewModelTask : TaskViewModel
    private var nowCalendar = Calendar.getInstance()
    private var dueDate: Date = nowCalendar.time
    private lateinit var todayChip: Chip
    private lateinit var tomorrowChip: Chip
    private lateinit var nextWeekChip: Chip
    private var priorityTask: Priority = Priority.NoPriority
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var isEdit: Boolean = false
    private lateinit var taskUpdate: Task

    private lateinit var uId :String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModelTask = ViewModelProvider(this).get(TaskViewModel::class.java)
        val view:View = inflater.inflate(R.layout.bottom_add_task, container, false)
        clickCalendar = view.findViewById(R.id.createDateTask)
        clickPriority = view.findViewById(R.id.createPriorityTask)
        nameTask = view.findViewById(R.id.nameTaskAdd)
        groupPriority = view.findViewById(R.id.group_priority)
        linerCalendar = view.findViewById(R.id.linerCalendar)
        calendar = view.findViewById(R.id.calendarView)
        saveTask = view.findViewById(R.id.createTask)
        todayChip = view.findViewById(R.id.today)
        tomorrowChip = view.findViewById(R.id.tomorrowDay)
        nextWeekChip = view.findViewById(R.id.nextWeek)

        uId = FirebaseAuth.getInstance().uid.toString()

        // Request focus and show soft keyboard automatically
        nameTask.requestFocus();
        getDialog()!!.getWindow()!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return view
    }

    override fun onResume() {
        // sharedviewmodel сохраняет данные и передает их из
        // одного активити в другое (это самая модель задачи и
        // bool переменная, которая показывает меняем мы задачу или добавляет
        isEdit = sharedViewModel.getIsEdit()
        sharedViewModel.chooseTask.observe(viewLifecycleOwner){
            if (isEdit) {
                nameTask.setText(it.nameTask)
                taskUpdate = it
                priorityTask = it.priorityTask
            }
        }

        super.onResume()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // сварачивание и открытие layout с календарем и кнопками завтра,
        // послезавтра и на след неделе
        clickCalendar.setOnClickListener {
            if (linerCalendar.visibility == View.GONE)
                linerCalendar.visibility = View.VISIBLE
            else
                linerCalendar.visibility = View.GONE
        }

        // сварачивание и открытие view приоритетов
        clickPriority.setOnClickListener {
            if (groupPriority.visibility == View.GONE)
                groupPriority.visibility = View.VISIBLE
            else
                groupPriority.visibility = View.GONE
        }

        // выбор приоритета через обработчика radioGroup,
        // отслеживаем id radiobutton, на которое нажали
        groupPriority.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.maxPriority -> priorityTask = Priority.Max
                R.id.middlePriority -> priorityTask = Priority.Middle
                R.id.minPriority -> priorityTask = Priority.Low
                else -> priorityTask = Priority.NoPriority
            }
        }

        // кнопки сегодня, завтра и на следующей неделе, ставят дату,
        // до которой должен быть выполнен task
        todayChip.setOnClickListener {
            nowCalendar.add(Calendar.DAY_OF_YEAR, 0)
            dueDate = nowCalendar.time

            Log.d("MyTimeResource", "$nowCalendar $dueDate")
        }

        tomorrowChip.setOnClickListener {
            nowCalendar.add(Calendar.DAY_OF_YEAR, 1)
            dueDate = nowCalendar.time

            Log.d("MyTimeResource", "$nowCalendar $dueDate")
        }

        nextWeekChip.setOnClickListener {
            nowCalendar.add(Calendar.DAY_OF_YEAR, 7)
            dueDate = nowCalendar.time

            Log.d("MyTimeResource", "$nowCalendar $dueDate")
        }


        // взять дату с большого календаря CalendarView
        // Сохраняем сегодняшнюю дату и дату на который день должны выполнить task
        calendar.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(calend: CalendarView, year: Int, mouth: Int, day: Int) {
                nowCalendar.set(year, mouth, day)
                dueDate = nowCalendar.time

                Log.d("TimeTaskLog", "$nowCalendar $dueDate ${Calendar.getInstance()}")
            }
        })

        //Кнопка сохранить задачу, здесь мы смотрим, если isEdit true, то значит
        // мы меняем задачу и по запросу updateTask меняем ее в бл, если isEdit false
        // берем модель Task и через viewModel сохраняем в базае данных через insertTask
        // Это добавление задачи
        saveTask.setOnClickListener {
            val name = nameTask.text.trim().toString()
            if (validTask(name)){

                if (isEdit){
                    taskUpdate.nameTask = name
                    taskUpdate.dateCreatedTask = nowCalendar.time
                    taskUpdate.priorityTask = priorityTask
                    viewModelTask.updateTask(taskUpdate)
                } else {
                    val task = Task(null, name, priorityTask,
                        dueDate, nowCalendar.time, uId)
                    viewModelTask.insertTask(task)
                }
            } else {
                Toast.makeText(requireContext(), "Введите задачу!", Toast.LENGTH_SHORT).show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    // Простая валидация, что присутсвует имя задачи
    private fun validTask(name:String):Boolean {
        if (name.isBlank())
            return false
        return true
    }


}