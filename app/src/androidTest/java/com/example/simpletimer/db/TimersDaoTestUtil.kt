package com.example.simpletimer.db

object TimersDaoTestUtil {

    fun createCountdownTimers(): List<TimerEntity> {
        return (1..3).map { TimerEntity("time_$it", it *1000L, it.toLong()) }
    }

}