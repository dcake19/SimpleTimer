package com.example.simpletimer

import android.app.Application
import android.content.Context
import com.example.simpletimer.create.FragmentCreateTimer
import com.example.simpletimer.dagger.AppComponent
import com.example.simpletimer.dagger.DaggerAppComponent
import com.example.simpletimer.timers.FragmentTimers

class SimpleTimersApplication: Application() {

    companion object{
        var application: SimpleTimersApplication? = null

        fun getContext(): Context? {
            return application?.applicationContext
        }
    }

    private var appComponent: AppComponent? = null
        get() = field?: DaggerAppComponent.builder().build()

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    fun inject(fragmentTimers: FragmentTimers) {
        appComponent?.inject(fragmentTimers)
    }

    fun inject(fragmentCreateTimer: FragmentCreateTimer) {
        appComponent?.inject(fragmentCreateTimer)
    }

}