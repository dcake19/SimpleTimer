package com.example.simpletimer.dagger

import com.example.simpletimer.create.FragmentCreateTimer
import com.example.simpletimer.timers.FragmentTimers
import dagger.Component
import javax.inject.Singleton

@Component(modules = [TimersModule::class])
@Singleton
interface AppComponent {
    fun inject(fragmentTimers: FragmentTimers)
    fun inject(fragmentCreateTimer: FragmentCreateTimer)
}