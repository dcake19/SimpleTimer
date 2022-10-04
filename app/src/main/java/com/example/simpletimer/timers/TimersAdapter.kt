package com.example.simpletimer.timers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.simpletimer.CountDownState
import com.example.simpletimer.CountDownTimerDisplay
import com.example.simpletimer.R
import com.example.simpletimer.getDisplayTime

class TimersAdapter(
    private val play: (timer: CountDownTimerDisplay) -> Unit,
    private val pause: (timer: CountDownTimerDisplay) -> Unit,
    private val stop: (timer: CountDownTimerDisplay) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    var timers: List<CountDownTimerDisplay> = emptyList()
        @Synchronized set(value) {
            field = value
            notifyDataSetChanged()
        }

    @Synchronized fun update(id: Long, timeMillis: Long) {
        val index = timers.indexOfFirst { it.id == id }
        if (index >= 0) {
            timers[index].currentTimeMillis = timeMillis
            notifyItemChanged(index)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TimerViewHolder((LayoutInflater.from(parent.context)
            .inflate(R.layout.timer_cell, parent, false)))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TimerViewHolder -> holder.set(timers[position])
        }
    }

    override fun getItemCount(): Int {
        return timers.size
    }

    inner class TimerViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
        private val play = itemView.findViewById<ImageButton>(R.id.play)
      //  private val pause = itemView.findViewById<ImageButton>(R.id.pause)

        fun set(timer: CountDownTimerDisplay) {
            val currentTime = itemView.findViewById<TextView>(R.id.currentTime)
            itemView.findViewById<TextView>(R.id.timerName).text = timer.name
            currentTime.text = getDisplayTime(timer.currentTimeMillis)

            play.apply {
                setOnClickListener {
                    timer.state = CountDownState.PLAYING
                    play(timer)
                   // pause.visibility = View.VISIBLE
                   // it.visibility = View.GONE
                }
             //   visibility = if (timer.state==CountDownState.STOPPED || timer.state==CountDownState.PAUSED) View.VISIBLE else View.GONE
            }
//            pause.apply {
//                setOnClickListener {
//                    timer.state = CountDownState.PAUSED
//                    pause(timer)
//                    play.visibility = View.VISIBLE
//                    it.visibility = View.GONE
//                }
//                visibility = if (timer.state==CountDownState.STOPPED || timer.state==CountDownState.PAUSED) View.GONE else View.VISIBLE
//            }
            val stop = itemView.findViewById<ImageButton>(R.id.stop)
            stop.setOnClickListener {
                timer.state = CountDownState.STOPPED
                stop(timer)
                timer.currentTimeMillis = timer.initialTimeMillis
                currentTime.text = getDisplayTime(timer.currentTimeMillis)
                play.visibility = View.VISIBLE
              //  pause.visibility = View.GONE
            }
        }
    }

}