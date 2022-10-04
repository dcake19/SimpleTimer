package com.example.simpletimer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            setNotificationChannels()

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        navGraph.setStartDestination(R.id.timers)
        navController.graph = navGraph
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannels() {
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).let {
            val timerChannel = NotificationChannel(
                ForegroundNotifications.TIMER_CHANNEL,
                "Timer",
                NotificationManager.IMPORTANCE_LOW
            )
            timerChannel.setShowBadge(false)
            it.createNotificationChannel(timerChannel)
        }
    }

    private val queuedTimers = mutableListOf<Pair<Long, Long>>()

    @Synchronized fun play(id: Long, initialTime: Long) {
        queuedTimers.add(Pair(id, initialTime))
        if (timerService == null) {
            val intent = Intent(this, TimerService::class.java)

            startService(intent)
            bindService(
                Intent(this, TimerService::class.java),
                timerServiceConnection,
                Context.BIND_AUTO_CREATE
            )
        } else {
            startTimers()
        }
    }

    fun stop(id: Long) {
        queuedTimers.removeAll { it.first == id }
        timerService?.stop(id)
    }

    @Synchronized private fun startTimers() {
        while (queuedTimers.isNotEmpty()) {
            val timer = queuedTimers.removeFirst()
            timerService?.start(timer.first, timer.second)
        }
    }

    private var timerService: TimerService? = null
    private var isTimerServiceBound = false

    private val timerServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            timerService = (binder as TimerService.TimerBinder).service
            isTimerServiceBound = true
            Log.v("timer_action", "connected")
            startTimers()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isTimerServiceBound = false
        }
    }


}