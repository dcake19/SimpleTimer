package com.example.simpletimer.create

interface CreateTimerViewModel {
    fun createTimer(name: String, hours: Int, minutes: Int, seconds: Int)
}