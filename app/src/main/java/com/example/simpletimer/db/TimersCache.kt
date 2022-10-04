package com.example.simpletimer.db

import com.example.simpletimer.CountDownTimer
import com.example.simpletimer.CountDownTimerDisplay
import io.reactivex.Flowable

interface TimersCache {
    fun create(countDownTimer: CountDownTimer)
    fun get(): Flowable<List<CountDownTimerDisplay>>
}