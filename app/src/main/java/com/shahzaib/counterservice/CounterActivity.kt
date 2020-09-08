package com.shahzaib.counterservice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CounterActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent: Intent = Intent(this, CounterService::class.java)
        startService(intent)
    }
}