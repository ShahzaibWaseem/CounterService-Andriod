package com.shahzaib.counterservice

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CounterActivity: AppCompatActivity() {
    private lateinit var countTextView: TextView

    private val mBroadcastReceiver = object : BroadcastReceiver()
    {
        override fun onReceive(context: Context?, intent: Intent?) {
           val counter = intent!!.getIntExtra("Counter", 0)
            onShowData(counter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.counter_activity)
    }

    override fun onStart() {
        super.onStart()

        val serviceRunning = isServiceRunning(CounterService::class.java)

        val intentFilter = IntentFilter()
        intentFilter.addAction(CounterService.INTENT_ACTION)
        registerReceiver(mBroadcastReceiver, intentFilter)

        if (!serviceRunning) {
            val intent: Intent = Intent(this, CounterService::class.java)
            startService(intent)
        }
    }

    fun onShowData(count: Int) {
        countTextView = findViewById(R.id.countText)
        countTextView.text = count.toString()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(mBroadcastReceiver)
    }

    private fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }
}