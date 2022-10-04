package com.example.simpletimer.timers

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpletimer.CountDownTimerDisplay
import com.example.simpletimer.db.TimersCache
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class TimersViewModelImpl(
    private val cache: TimersCache,
    private val _timersLiveData: MutableLiveData<List<CountDownTimerDisplay>>
    ): TimersViewModel {

    override val timersLiveData: LiveData<List<CountDownTimerDisplay>>
        get() = _timersLiveData

    init {
        cache
            .get()
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .subscribe(
                { _timersLiveData.postValue(it) },
                {}
            )
    }
}