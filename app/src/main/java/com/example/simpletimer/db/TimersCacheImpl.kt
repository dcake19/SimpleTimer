package com.example.simpletimer.db

import com.example.simpletimer.CountDownTimer
import com.example.simpletimer.CountDownTimerDisplay
import io.reactivex.Flowable

class TimersCacheImpl(timerDatabaseProvider: TimerDatabaseProvider): TimersCache {

    private val timerDao = timerDatabaseProvider.getTimerDao()

    override fun create(countDownTimer: CountDownTimer) {
        Thread {
            timerDao.insert(TimerEntity(countDownTimer.name, countDownTimer.timeMillis))
        }.start()
    }

    override fun get(): Flowable<List<CountDownTimerDisplay>> {
        return timerDao.getTimers().map { it.map { t -> CountDownTimerDisplay(t.id, t.name, t.timeMillis) } }
    }
}