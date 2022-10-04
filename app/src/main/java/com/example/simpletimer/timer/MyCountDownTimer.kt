package com.example.simpletimer.timer

import android.util.Log
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class MyCountDownTimer(
    private val id: Long,
    private val initialMilliseconds: Long
): MyTimer {
    private val period = 1000L

    private var disposable: Disposable? = null

    override fun start(updateTime: (id: Long, time: Long) -> Unit) {
        Observable.interval(period, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(Schedulers.computation())
            .map { initialMilliseconds - (it+1) * period }
            .subscribe(object : Observer<Long> {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: Long) {
                    updateTime(id, t)
                    if (t<=0) {
                        disposable?.dispose()
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }

            })
    }

    override fun stop() {
        disposable?.dispose()
    }
}