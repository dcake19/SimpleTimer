package com.example.simpletimer.create

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.simpletimer.R
import com.example.simpletimer.SimpleTimersApplication
import com.example.simpletimer.SimpleTimersApplication.Companion.application
import javax.inject.Inject

class FragmentCreateTimer: Fragment(R.layout.create_timer_fragment) {

    @Inject lateinit var viewModel: CreateTimerViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (application as SimpleTimersApplication).inject(this)

        val timerName = view.findViewById<EditText>(R.id.timerName)
        val timeHours = view.findViewById<EditText>(R.id.timeHours)
        val timeMinutes = view.findViewById<EditText>(R.id.timeMinutes)
        val timeSeconds = view.findViewById<EditText>(R.id.timeSeconds)

        setTimeMaxValues(timeHours, 999)
        setTimeMaxValues(timeMinutes, 59)
        setTimeMaxValues(timeSeconds, 59)

        view.findViewById<Button>(R.id.create).setOnClickListener {
            create(timerName, timeHours, timeMinutes, timeSeconds)
        }
    }

    private fun setTimeMaxValues(et: EditText, maxValue: Int) {
        val chars = if (maxValue > 100) 3 else 2

        et.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {

                if(event?.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                    if (chars == 3)
                        et.setText("000")
                    else
                        et.setText("00")
                    return true
                }

                if(event?.action == KeyEvent.ACTION_DOWN && keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                    et.setText(getText(et.text.toString(), (keyCode-7).toString()))
                    return true
                }

                return false
            }

        })

        et.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    if (chars == 3)
                        et.setText("000")
                    else
                        et.setText("00")
                }
            }
        }

        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if ((s?.toString()?.toInt() ?: maxValue) > maxValue)
                    et.setText(maxValue.toString())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun getText(oldText: String, newChar: String): String {
        return (oldText + newChar).drop(1)
    }

    private fun create(
        name: EditText,
        hour: EditText,
        minutes: EditText,
        seconds: EditText
    ) {
        val timerName = name.text.toString().ifEmpty { name.hint.toString() }

        viewModel.createTimer(
            timerName,
            hour.text.toString().toInt(),
            minutes.text.toString().toInt(),
            seconds.text.toString().toInt()
        )
    }
}