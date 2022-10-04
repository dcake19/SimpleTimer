package com.example.simpletimer.timers

import androidx.lifecycle.LiveData
import com.example.simpletimer.CountDownTimerDisplay

interface TimersViewModel {
    val timersLiveData: LiveData<List<CountDownTimerDisplay>>
}