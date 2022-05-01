package com.example.diplomapplication.ui.TimerUI

import android.app.*
import android.content.*
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDeepLinkBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diplomapplication.MainActivity
import com.example.diplomapplication.R
import com.example.diplomapplication.Service.MyTimeService
import com.example.diplomapplication.databinding.FragmentSlideshowBinding
import com.example.diplomapplication.room.timerPomadoro.TimerModel
import com.example.diplomapplication.room.timerPomadoro.TimerViewModel
import com.example.diplomapplication.ui.TimerUI.adapterTimer.adapterTimer
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import kotlin.collections.ArrayList

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding

    // позиция перехода от указания времени работы до начало отсчета таймера
    private var stage: Int = 1

    // сами таймеры и кол-во повторов
    private lateinit var timerPickerWork: TimePicker
    private lateinit var timerPickerRelax: TimePicker
    private lateinit var quantityTimer: NumberPicker

    // fullList хранить все model таймера из БД
    // changeList хранит отсортированные model от fullList
    private val fullList : ArrayList<TimerModel> = ArrayList()
    private val changeList : ArrayList<TimerModel> = ArrayList()

    // кол-во минут работы, учебы и повторов
    private var workTimeMinute: Long = 0
    private var relaxTimerMinute : Long = 0
    private var quantity: Int = 0

    // на каком цикле находиться сейчас таймер
    private var countTime : Int = 0

    // начат таймер или нет и сейчас идет отдыха или работа
    private var isStarted: Boolean = false
    private var Relax : Boolean = false


    private lateinit var auth: FirebaseAuth
    private lateinit var uID: String

    // выбраная дата для сортировки выходных данных
    private lateinit var selectDate : Calendar

    // модель для просмотра был заупщен или нет таймер, если вышли из приложения (но не закрыли)
    private val sharedViewModel: ViewModelTimer by activityViewModels()
    private val timerDb : TimerViewModel by activityViewModels()

    // для сохранения данных, при выходе из приложения
    private lateinit var preferences: SharedPreferences

    companion object{
        // для сохранения данных минут работы, учебы и повторов
        private val getWorkMinute = "getWork"
        private val getRelaxMinute = "getRelax"
        private val getCountTime = "getTime"

        // для уведолмения
        private val CHANEL_ID = 101
        private val CHANEL_NAME = "chanel_name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        uID = auth.uid.toString()

        // инициализация календаря
        selectDate = Calendar.getInstance()

        // работа с временем работы (инициализация, установка 24 часа, постановка дефолтных значений)
        timerPickerWork = binding.timerPickerWork
        timerPickerWork.setIs24HourView(true);
        timerPickerWork.currentHour = 0
        timerPickerWork.currentMinute = 0
        timerPickerWork.setOnTimeChangedListener { timePicker, hour, minute ->
            // поставить кол-во минут работы
            workTimeMinute = (minute + (hour / 60 % 60)).toLong()
        }

        // работа с временем отдыха (инициализация, установка 24 часа, постановка дефолтных значений)
        timerPickerRelax = binding.timerPickerRelax
        timerPickerRelax.setIs24HourView(true)
        timerPickerRelax.currentHour = 0
        timerPickerRelax.currentMinute = 0
        timerPickerRelax.setOnTimeChangedListener { timePicker, hour, minute->
            // кол-во минут отдыха
            relaxTimerMinute = minute + (hour / 60 % 60).toLong()
        }

        // кол-во повторов
        quantityTimer = binding.quantityTimer
        quantityTimer.setOnValueChangedListener { numberPicker, oldNumber, nowNumber ->
            quantity = nowNumber
        }
        // ограничения кол-во повторов для выбора
        quantityTimer.minValue = 0
        quantityTimer.maxValue = 9
        // ????
        quantityTimer.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        // работа с переходами до старта таймера
        binding.startTimer.setOnClickListener {
            nextStageTimer()
        }

        // остановка работы (сброс) и поставить дефолтные значений минут и секунд
        binding.stopTimer.setOnClickListener {
            restartTimer()
            stopTimer()
        }

        // сохранения данных для выхода из прил
        preferences = requireContext().getSharedPreferences(getWorkMinute, Context.MODE_PRIVATE)

        // установка адаптера
        setAdapter()

        // выбор даты для сортировки адаптера (БД Таймера)
        binding.dateChoosenTimer.setOnClickListener {
            val yearC = selectDate.get(Calendar.YEAR)
            val monthC = selectDate.get(Calendar.MONTH)
            val dayC = selectDate.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireActivity(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                selectDate.set(year, monthOfYear, dayOfMonth)
                changeAdapter()

                Log.d("MyDateSelecte", "$dayOfMonth ${dayC}" )
            }, yearC, monthC, dayC)
            dpd.show()
        }

        // вывести все что есть в БД таймера
        binding.allTiemr.setOnClickListener {
            changeList.clear()
            changeList.addAll(fullList)
            setAdapter()
        }

        return binding.root
    }

    private fun nextStageTimer() {
        val textChoose = binding.textTimeChoose
        when (stage){
            1 -> {
                textChoose.text = "Выберите интерва работы:"
                // показ нужной view и скрытие не нужных и валидация, что не стоит 0
                timerPickerWork.visibility = View.VISIBLE
                timerPickerRelax.visibility = View.GONE
                quantityTimer.visibility = View.GONE
                binding.minutes.visibility = View.GONE
                binding.circleTimer.visibility = View.GONE
                if(validTimer(workTimeMinute)){
                    stage += 1
                }
            }
            2 -> {
                textChoose.text = "Выберите интервал отдыха:"
                timerPickerWork.visibility = View.GONE
                timerPickerRelax.visibility = View.VISIBLE
                if(validTimer(relaxTimerMinute)){
                    stage += 1
                }
            }
            3 -> {
                textChoose.text = "Выберите кол-во повторений:"
                timerPickerRelax.visibility = View.GONE
                quantityTimer.visibility = View.VISIBLE
                if(validTimer(quantity.toLong())){
                    stage += 1
                }
            }
            4 -> {
                textChoose.text = "Таймер помадоро:"
                quantityTimer.visibility = View.GONE
                binding.minutes.visibility = View.VISIBLE
                binding.circleTimer.visibility = View.VISIBLE
                stage++
            }
            else -> {
                // осхранние данных
                getShared()
                // добавление в БД рез таймера
                addListTimer()
                // значит что таймер запущен, это если мы решим прикрыть приложение
                sharedViewModel.setIsEdit(true)
                textChoose.text = "Работаем!"
                // начало отсчета
                pauseOrStart()
            }
        }
    }


    // широковещательный канал (идет в отедльном потоке)
    private val broadcast = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, getTime: Intent?) {
            timerPickerWork.visibility = View.GONE
            timerPickerRelax.visibility = View.GONE
            quantityTimer.visibility = View.GONE
            binding.minutes.visibility = View.VISIBLE
            binding.circleTimer.visibility = View.VISIBLE

            val time = getTime!!.getLongExtra(MyTimeService.TAG, 0L)
            binding.minutes.text = getTimeStringFormat(time)


            // считываем процент и отображаем его в progressbar
            if (!Relax){
                // формула процента от числа
                val procentProgress =  (time * 100) /(workTimeMinute * (1000 * 60))
                binding.circleTimer.progress = procentProgress.toInt()
                Log.d("ProcentProgress", "$procentProgress $time ${workTimeMinute * (1000 * 60)}")
            } else if (Relax) {
                val procentProgress =  (time * 100) /(relaxTimerMinute * (1000 * 60))
                binding.circleTimer.progress = procentProgress.toInt()
            }

            // проверяем время уже равно 0 или нет
            val res = (time / 1000) % 60
            if (res == 0L){
                // этот if если цикл таймера закончен
                if (countTime == quantity){
                    binding.circleTimer.progress = 100
                    binding.textTimeChoose.text = "Таймер-помадоро завершен"
                    Relax = false
                    isStarted = true
                    stopTimer()
                    createNofit("Таймер завершен!")
                }

                // это если работает
                if (!Relax && countTime < quantity){
                    binding.circleTimer.progress = 0
                    binding.textTimeChoose.text = "Отдыхаем!"
                    Relax = true
                    isStarted = false
                    createNofit("Можно отдохнуть...")
                    stopTimer()
                    pauseOrStart()
                    countTime++
                } else if (Relax && countTime < quantity) { // это если отдыхаем
                    binding.circleTimer.progress = 0
                    binding.textTimeChoose.text = "Работаем"
                    Relax = false
                    isStarted = true
                    stopTimer()
                    pauseOrStart()
                    createNofit("Пора за работу...")
                }
            }

        }
    }


    // это уведомление
    private fun createNofit(name: String) {

        // погугли (здесь при нажатие мы переходим на фрагмент с таймером)
        val pendingIntent = NavDeepLinkBuilder(requireContext())
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.mobile_navigation)
            .setDestination(R.id.nav_slideshow)
            .createPendingIntent()

        // строим уведомление
        val builder = NotificationCompat.Builder(requireContext(), CHANEL_NAME)
            .setSmallIcon(R.drawable.iam)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle("DiplomApplication")
            .setContentText(name)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent) // c помощью этого перебрасываем к фрагменту
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)


        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            // создаем канал для уведомлений
            val notificationChannel = NotificationChannel(CHANEL_NAME, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(CHANEL_ID, builder.build())

    }

    // перевод из миллисекунд в минуты и секунды
    private fun getTimeStringFormat(time: Long): String {
        val seconds = (time / 1000) % 60
        val minutes = ((time / (1000*60)) % 60)
        return makeTimeString(minutes, seconds)
    }

    // конвертация в строку минут и миллесекунды
    private fun makeTimeString(minutes: Long, second: Long): String =
        String.format("%02d : %02d", minutes, second)

    private fun pauseOrStart(){

        if (!isStarted) {
            if (!Relax){
                // IntentFilter загугли
                requireContext().registerReceiver(broadcast, IntentFilter(MyTimeService.COUNTDOWN_BR));
                requireContext().startService(
                    Intent(requireContext(), MyTimeService::class.java).apply {
                        putExtra(MyTimeService.getTime, workTimeMinute)
                    }
                )
                isStarted = true
            } else {
                requireContext().registerReceiver(broadcast, IntentFilter(MyTimeService.COUNTDOWN_BR));
                requireContext().startService(
                    Intent(requireContext(), MyTimeService::class.java).apply {
                        putExtra(MyTimeService.getTime, relaxTimerMinute)
                    }
                )
                isStarted = true
            }
        }

    }

    // отправка через SharedPreferecne значения
    private fun getShared() {
        preferences.edit().putLong(getWorkMinute, workTimeMinute)
        preferences.edit().putLong(getRelaxMinute, relaxTimerMinute)
        preferences.edit().putInt(getCountTime, quantity)
    }


    // оставить широковещательный канал и сервис и сброс таймера на дефолтные значения
    private fun stopTimer(){
        try {
            requireContext().unregisterReceiver(broadcast)
            requireContext().stopService(Intent(requireContext(), MyTimeService::class.java))
            onResetTimer()
        } catch (e : Exception){
            Toast.makeText(requireContext(), "Все сбросилось!", Toast.LENGTH_SHORT).show()
        }
    }

    // сброс таймера, стадии, прогрессбара, установка Relax и isStarted
    private fun restartTimer() {
        sharedViewModel.setIsEdit(false)
        stage = 1
        Relax = false
        isStarted = false
        binding.circleTimer.progress = 100
        nextStageTimer()
    }

    // сброс минут на экране
    private fun onResetTimer() {
        binding.minutes.text = String.format("%02d : %02d", 0, 0)
    }


    // валидация для выбора минут работы, отдыха и кол-во повторов
    private fun validTimer(timeValid: Long): Boolean{
        if (timeValid == 0L){
            Toast.makeText(requireContext(), "Поставьте корректные данные!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // доавбление в БД новую модель Таймера
    private fun addListTimer() {
        val timer = TimerModel(null, workTimeMinute, relaxTimerMinute, quantity, Calendar.getInstance().time, uID)
        timerDb.insertTimer(timer)
        setAdapter()
    }

    // установка адапетра, скрытие кнопок, если список пуст или откртыие, если пополняется
    private fun setAdapter(){

        timerDb.getTimer().observe(viewLifecycleOwner){

            if (it.isEmpty()){
                binding.nameIndicatorTask.visibility = View.GONE
            } else {
                binding.nameIndicatorTask.visibility = View.VISIBLE
            }

            // очистка сипска fulllist и доавбление всех элементов из БД
            fullList.clear()
            fullList.addAll(it)
            binding.timerRecycle.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.VERTICAL, false)
            binding.timerRecycle.adapter = adapterTimer(requireContext(), changeList)
        }
    }

    // изменение адаптера под нужный Uid и дате
    private fun changeAdapter(){

        changeList.clear()
        for (i in fullList){
            if (i.uID == uID && selectDate.time.date.equals(i.dateTime!!.date)){
                changeList.add(i)
            }
        }

        setAdapter()
    }

    override fun onPause() {
        sharedViewModel.setOpen(false)
        super.onPause()
    }

    override fun onResume() {
        // получить значения при закрытие приложения с запущенным таймером
        if (sharedViewModel.getIsEdit()){
            sharedViewModel.setOpen(true)
            timerPickerWork.visibility = View.GONE
            timerPickerRelax.visibility = View.GONE
            quantityTimer.visibility = View.GONE
            binding.minutes.visibility = View.VISIBLE
            stage = 5
            workTimeMinute = preferences.getLong(getWorkMinute, 0L)
            relaxTimerMinute = preferences.getLong(getRelaxMinute, 0L)
            quantity = preferences.getInt(getCountTime, 0)

            pauseOrStart()
        }
        super.onResume()
    }
}