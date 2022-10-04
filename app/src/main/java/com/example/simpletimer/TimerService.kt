package com.example.simpletimer

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.simpletimer.timer.MyCountDownTimer

class TimerService: Service() {

//    private val timerActionReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            Log.v("timer_action", "received")
//            intent?.getIntExtra(ID,-1)
//                ?.takeUnless { it ==-1 }
//                ?.let {
//                    when (intent.getSerializableExtra(ACTION) as TimerAction) {
//                        TimerAction.START -> {
//                            timer = MyCountDownTimer(intent.getLongExtra(TIME, 0))
//                            timer?.start(::updateTime)
//                        }
//                        TimerAction.PAUSE -> {}
//                        TimerAction.STOP -> {}
//                        TimerAction.RESUME -> {}
//                    }
//                }
//        }
//    }

    private fun updateTime(id: Long, milliseconds: Long) {
      //  val time = getCountDownTime(milliseconds)
       // Log.v("my_time", milliseconds.toString())
      //  Log.v("my_time", "${time.hour}:${time.minute}:${time.second}")
        if (milliseconds == 0L) {
            timers.remove(id)
        }

        val intent = Intent("timer")
        intent.putExtra(ID, id)
        intent.putExtra(TIME, milliseconds)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }


    inner class TimerBinder : Binder() {
        val service: TimerService
            get() = this@TimerService
    }

    private val binder: Binder = TimerBinder()

    private var notificationBuilder: NotificationCompat.Builder? = null

    //var timer: MyCountDownTimer? = null

    private val timers = hashMapOf<Long, MyCountDownTimer>()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

      //  LocalBroadcastManager.getInstance(this)
        //    .registerReceiver(timerActionReceiver, IntentFilter("timer_action"))

        intent?.let {
            notificationBuilder = NotificationCompat
                .Builder(this, ForegroundNotifications.TIMER_CHANNEL)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Timer")
                .setPriority(NotificationCompat.PRIORITY_LOW)

            notificationBuilder?.let {
                startForeground(ForegroundNotifications.TIMER_ID, it.build())
            }
        }

        return START_NOT_STICKY
    }

    fun start(id: Long, initialTime: Long) {
        if (!timers.containsKey(id)) {
            timers[id] = MyCountDownTimer(id, initialTime)
            timers[id]?.start(::updateTime)
        }
    }

    fun stop(id: Long) {
        val timer = timers.remove(id)
        timer?.stop()
    }

}