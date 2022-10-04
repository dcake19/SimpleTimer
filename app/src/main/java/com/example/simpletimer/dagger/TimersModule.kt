package com.example.simpletimer.dagger

import androidx.lifecycle.MutableLiveData
import com.example.simpletimer.SimpleTimersApplication
import com.example.simpletimer.create.CreateTimerViewModel
import com.example.simpletimer.create.CreateTimerViewModelImpl
import com.example.simpletimer.db.TimerDatabaseProvider
import com.example.simpletimer.db.TimersCache
import com.example.simpletimer.db.TimersCacheImpl
import com.example.simpletimer.timers.TimersViewModel
import com.example.simpletimer.timers.TimersViewModelImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TimersModule {

    @Singleton
    @Provides
    fun provideTimersViewModel(cache: TimersCache): TimersViewModel {
        return TimersViewModelImpl(cache, MutableLiveData())
    }

    @Singleton
    @Provides
    fun provideCreateViewModel(cache: TimersCache): CreateTimerViewModel {
        return CreateTimerViewModelImpl(cache)
    }

    @Singleton
    @Provides
    fun provideTimersCache(timerDatabaseProvider: TimerDatabaseProvider): TimersCache {
        return TimersCacheImpl(timerDatabaseProvider)
    }

    @Singleton
    @Provides
    fun provideTimerDatabaseProvider(): TimerDatabaseProvider {
        return TimerDatabaseProvider(SimpleTimersApplication.getContext()!!)
    }
}