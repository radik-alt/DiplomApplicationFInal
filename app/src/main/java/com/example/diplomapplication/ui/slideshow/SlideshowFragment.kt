package com.example.diplomapplication.ui.slideshow

import android.app.TimePickerDialog
import android.content.*
import android.content.res.Resources
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diplomapplication.R
import com.example.diplomapplication.Service.MyTimer
import com.example.diplomapplication.databinding.FragmentSlideshowBinding
import com.example.diplomapplication.room.timerPomadoro.TimerModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var stage: Int = 1
    private lateinit var timerPickerWork: TimePicker
    private lateinit var timerPickerRelax: TimePicker
    private lateinit var quantityTimer: NumberPicker
    private var workTime: Int = 0
    private lateinit var relaxTimer: String
    private var quantity: Int = 0

    private var list: ArrayList<TimerModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        binding.background!!.setBackgroundColor(androidx.appcompat.R.attr.colorPrimary)

        timerPickerWork = binding.timerPickerWork
        timerPickerWork.setOnTimeChangedListener { timePicker, hour, minute ->
//            Toast.makeText(this, "$i $i2", Toast.LENGTH_SHORT).show()
            val minuteTemp = Calendar.getInstance().time.minutes
//            Log.d("TIMER_LOG", "$minuteTemp")
            workTime = minute - minuteTemp
        }

        timerPickerRelax = binding.timerPickerRelax
        timerPickerRelax.setOnTimeChangedListener { timePicker, hour, minute->
//            Toast.makeText(this, "$i $i2", Toast.LENGTH_SHORT).show()
            relaxTimer = makeTimeString(hour, minute)
        }

        quantityTimer = binding.quantityTimer
        quantityTimer.setOnValueChangedListener { numberPicker, oldNumber, nowNumber ->
//            Toast.makeText(this, "$i $i2", Toast.LENGTH_SHORT).show()
            quantity = nowNumber
        }
        quantityTimer.minValue = 1
        quantityTimer.maxValue = 9
        quantityTimer.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        binding.startTimer.setOnClickListener { nextStageTimer() }
        binding.stopTimer.setOnClickListener { onResetTimer() }
        binding.pauseTimer.setOnClickListener { pauseTimer() }

        serviceIntent = Intent(requireContext(), MyTimer::class.java)
        requireContext().registerReceiver(updateTime, IntentFilter(MyTimer.TIME_UPDATE))
        return binding.root
    }

    private fun nextStageTimer() {
        var textChoose = binding.textTimeChoose
        when (stage){
            1 -> {
                textChoose.text = "Выберите интерва работы:"
                timerPickerWork.visibility = View.VISIBLE
                timerPickerRelax.visibility = View.GONE
                quantityTimer.visibility = View.GONE
                binding.minutes.visibility = View.GONE
                stage += 1
            }
            2 -> {
                textChoose.text = "Выберите интервал отдыха:"
                timerPickerWork.visibility = View.GONE
                timerPickerRelax.visibility = View.VISIBLE
                stage++
            }
            3 -> {
                textChoose.text = "Выберите кол-во повторений:"
                timerPickerRelax.visibility = View.GONE
                quantityTimer.visibility = View.VISIBLE
                stage++
            }
            4 -> {
                textChoose.text = "Таймер помадоро:"
                quantityTimer.visibility = View.GONE
                binding.minutes.visibility = View.VISIBLE
                stage++
                startTimer()
            }
            else -> {
                Toast.makeText(requireContext(), "I don't knotw", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            time = intent!!.getDoubleExtra(MyTimer.TIME_EXTRA, 0.0)
            val temp = time.roundToInt() / 60 % 60
            if (temp == workTime) {
                val player = MediaPlayer.create(requireContext(), R.raw.rington)
                player.start()
//                var player = MediaPlayer()
                Log.d("TIMER_LOG", "Загудел")
            }
            Log.d("TIMER_LOG", "$temp $workTime")

            binding.minutes.text = getTimeStringFormat(time)
        }
    }

    private fun getTimeStringFormat(time: Double): String {
        val resultInt = time.roundToInt()
        val minutes = resultInt / 60 % 60
        val second = resultInt / 1 % 60;
//        Log.d("TIMER_LOG", "$minutes $second")
        return makeTimeString(minutes, second)
    }

    private fun makeTimeString(minutes: Int, second: Int): String =
        String.format("%02d : %02d", minutes, second)


    private fun pauseTimer(){

    }

    private fun startTimer() {
        serviceIntent = Intent(requireContext(), MyTimer::class.java)
        requireContext().startService(serviceIntent)
    }

    private fun stopTimer() {
        stage = 1
//        val model = TimerModel(1, workTime, relaxTimer, quantity)
//        list.add(model)
        nextStageTimer()
        requireContext().stopService(serviceIntent)
    }

    private fun onResetTimer() {
        stopTimer()
        time = 0.0
        binding.minutes.text = String.format("%02d : %02d", 0, 0)
    }

}