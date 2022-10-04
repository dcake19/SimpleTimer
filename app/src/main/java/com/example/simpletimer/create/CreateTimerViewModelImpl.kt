package com.example.simpletimer.create

import com.example.simpletimer.CountDownTimer
import com.example.simpletimer.db.TimersCache
import com.example.simpletimer.getTimeMillis

class CreateTimerViewModelImpl(private val cache: TimersCache): CreateTimerViewModel {

    override fun createTimer(name: String, hours: Int, minutes: Int, seconds: Int) {
        cache.create(CountDownTimer(name, getTimeMillis(hours, minutes, seconds)))
    }

}