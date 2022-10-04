package com.example.simpletimer.timer

interface MyTimer {
    fun start(updateTime: (id: Long, time: Long) -> Unit)
    fun stop()
}