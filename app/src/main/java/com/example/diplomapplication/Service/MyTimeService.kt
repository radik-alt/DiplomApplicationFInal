package com.example.diplomapplication.Service

import android.app.Service
import android.content.Intent
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyTimeService : Service() {

    companion object{
        public val getTime = "GetTime"
        public val TAG = "BroadcastService"
        public val COUNTDOWN_BR = "your_package_name.countdown_br"
    }

    var generalIntent = Intent(COUNTDOWN_BR)

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val get = intent!!.getLongExtra(getTime, 0L)
        val milli = get * (1000 * 60)
        Log.d(getTime, "$milli")
        myTimer(milli)
        return START_STICKY
    }

    // обратный осчет
    private fun myTimer(timeMilles: Long) {
        countDownTimer = object : CountDownTimer(timeMilles, 1000){
            override fun onTick(milisecond: Long) {
                generalIntent.putExtra(TAG, milisecond)
                sendBroadcast(generalIntent)
            }

            override fun onFinish() {
            }
        }.start()

    }

    override fun onDestroy() {
        countDownTimer.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? = null
}