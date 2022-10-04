package com.example.simpletimer

data class CountDownTimer(val name: String, val timeMillis: Long)

data class CountDownTimerDisplay(
    val id: Long,
    val name: String,
    val initialTimeMillis: Long
    ) {
    var currentTimeMillis = initialTimeMillis
    var state: CountDownState = CountDownState.STOPPED
}

enum class CountDownState{
    STOPPED, PLAYING, PAUSED
}
