package com.example.simpletimer.timers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.simpletimer.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class FragmentTimers(private val inject: Boolean = true): Fragment(R.layout.fragment_timers) {

    @Inject lateinit var viewModel: TimersViewModel

    private lateinit var timersAdapter: TimersAdapter

    private val timerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            intent?.getLongExtra(ID,-1)
                ?.takeUnless { it ==-1L }
                ?.let { id ->
                    intent.getLongExtra(TIME,-1)
                        .takeUnless { it ==-1L }
                        ?.let { time ->
                           // Log.v("timer_received", it.toString())
                            timersAdapter.update(id, time)
                        }
                }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (inject)
        (requireActivity().application as SimpleTimersApplication).inject(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createRecyclerView(view)

        view.findViewById<FloatingActionButton>(R.id.addTimer)
            .setOnClickListener {
                Navigation
                    .findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.action_timers_to_create_timers)
            }

        viewModel.timersLiveData.observe(viewLifecycleOwner) { displayTimers(it) }
    }

    private fun createRecyclerView(view: View) {
        timersAdapter = TimersAdapter(::play, ::pause, ::stop)
        val timersList = view.findViewById<RecyclerView>(R.id.timers_list)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        timersList.layoutManager = linearLayoutManager
        (timersList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        timersList.adapter = timersAdapter

    }

    private fun displayTimers(timers: List<CountDownTimerDisplay>) {
        timersAdapter.timers = timers
          LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(timerReceiver, IntentFilter("timer"))
    }

    private fun play(timer: CountDownTimerDisplay) {
        activity?.let {
            (it as MainActivity).play(timer.id, timer.initialTimeMillis)
        }
    }

    private fun pause(timer: CountDownTimerDisplay) {

    }

    private fun stop(timer: CountDownTimerDisplay) {
        activity?.let {
            (it as MainActivity).stop(timer.id)
        }
    }
}