package com.example.simpletimer.db

import android.content.Context
import androidx.room.Room

class TimerDatabaseProvider(private val context: Context) {

    fun getTimerDao(): TimerDao {
        return Room
            .databaseBuilder(
                context,
                TimerDatabase::class.java,
                DatabaseName.TIMERS_DB)
            .build()
            .timerDao()
    }
}