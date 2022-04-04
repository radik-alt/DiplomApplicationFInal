package com.example.diplomapplication.ui.slideshow

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.diplomapplication.Service.MyTimer
import com.example.diplomapplication.databinding.FragmentSlideshowBinding
import java.util.*
import kotlin.math.roundToInt

class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private lateinit var serviceIntent: Intent
    private var timerStarted = false
    private var time = 0.0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
        binding = FragmentSlideshowBinding.inflate(inflater, container, false)

        binding.startTimer.setOnClickListener { onStartStopTimer() }
        binding.stopTimer.setOnClickListener { onResetTimer() }

        serviceIntent = Intent(requireContext(), MyTimer::class.java)
        requireContext().registerReceiver(updateTime, IntentFilter(MyTimer.TIME_UPDATE))
        return binding.root
    }


    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(p0: Context?, intent: Intent?) {
            time = intent!!.getDoubleExtra(MyTimer.TIME_EXTRA, 0.0)
            Log.d("TIMER_LOG", getTimeStringFormat(time))
            binding.minutes.text = getTimeStringFormat(time)
        }
    }

    private fun getTimeStringFormat(time: Double): String {
        val resultInt = time.roundToInt()
        val minutes = resultInt / 60 % 60
        val second = resultInt / 1 % 60;
        Log.d("TIMER_LOG", "$minutes $second")
        return makeTimeString(minutes, second)
    }

    private fun makeTimeString(minutes: Int, second: Int): String =
        String.format("%02d : %02d", minutes, second)


    private fun onStartStopTimer() {
        if (timerStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent = Intent(requireContext(), MyTimer::class.java)
        requireContext().startService(serviceIntent)
        timerStarted = true
    }

    private fun stopTimer() {
        requireContext().stopService(serviceIntent)
        timerStarted = false
    }

    private fun onResetTimer() {
        stopTimer()
        time = 0.0
        binding.minutes.text = String.format("%02d : %02d", 0, 0)
    }

}