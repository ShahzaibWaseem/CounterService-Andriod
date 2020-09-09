package com.shahzaib.counterservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CounterActivity: AppCompatActivity() {
    private lateinit var counterReceiver: CounterReceiver
    private lateinit var countTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_activity)
    }

    override fun onStart() {
        super.onStart()

        val intent: Intent = Intent(this, CounterService::class.java)
        startService(intent)
    }

    class CounterReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val counter: Int = intent!!.getIntExtra("Counter", 0)
            Toast.makeText(context, "Counter: $counter", Toast.LENGTH_SHORT).show()
        }
    }
}