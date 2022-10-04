package com.example.simpletimer.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.schedulers.TestScheduler
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TimerDaoTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var db: TimerDatabase
    private lateinit var dao: TimerDao

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, TimerDatabase::class.java).build()
        dao = db.timerDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insert_singleTimer() {
        val timer = TimersDaoTestUtil
            .createCountdownTimers()
            .first()

        dao.insert(timer)

        dao.getTimers()

        val timers = dao.getTimers().test().values().first()

        assertThat(timers, `is`(listOf(timer)))
    }

    @Test
    fun insert_multipleTimers() {
        val timers = TimersDaoTestUtil
            .createCountdownTimers()

        dao.insert(timers[0])
        dao.insert(timers[1])
        dao.insert(timers[2])

        dao.getTimers()

        val timersDb = dao.getTimers().test().values().first()

        assertThat(timersDb, `is`(timers))
    }


}