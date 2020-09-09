package com.shahzaib.counterservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CounterActivity: AppCompatActivity() {
    private lateinit var countTextView: TextView

    private val mBroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
           val counter = intent!!.getIntExtra("Counter", 0)
            Toast.makeText(context, "Counter: $counter", Toast.LENGTH_SHORT).show()
            onShowData(counter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_activity)
    }

    override fun onStart() {
        super.onStart()

        val intentFilter = IntentFilter()
        intentFilter.addAction(CounterService.INTENT_ACTION)
        registerReceiver(mBroadcastReceiver, intentFilter)

        val intent: Intent = Intent(this, CounterService::class.java)
        startService(intent)
    }

    fun onShowData(count: Int) {
        countTextView = findViewById(R.id.countText)

        countTextView.text = count.toString()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mBroadcastReceiver)
    }

}