package com.example.simpletimer.ui

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpletimer.CountDownTimerDisplay
import com.example.simpletimer.R
import com.example.simpletimer.timers.FragmentTimers
import com.example.simpletimer.timers.TimersViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class TimersUITests {

    @Mock lateinit var viewModel: TimersViewModel

    private val timersLiveData = MediatorLiveData<List<CountDownTimerDisplay>>()

    @Before
    fun before() {
        MockitoAnnotations.initMocks(this)
    }

    private fun launchFragment(navController: NavController?=null){
        val fragment = FragmentTimers(false)
        fragment.viewModel = viewModel
        val scenario = launchFragmentInContainer(Bundle(), R.style.Theme_SimpleTimer) {
            fragment
        }

        if (navController!=null) {
            scenario.onFragment {
                Navigation.setViewNavController(it.requireView(), navController)
            }
        }
    }

    @Test
    fun display_singleTimer() {
        val navController = Mockito.mock(NavController::class.java)

        Mockito.`when`(viewModel.timersLiveData).thenReturn(timersLiveData)
        launchFragment(navController)
        timersLiveData.postValue(listOf(CountDownTimerDisplay(1, "Timer Name", 60*1000L)))

        onView(withId(R.id.timerName)).check(matches(withText("Timer Name")))
    }

}