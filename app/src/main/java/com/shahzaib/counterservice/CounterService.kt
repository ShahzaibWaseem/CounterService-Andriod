package com.shahzaib.counterservice

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast

class CounterService: Service(){
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            try {
                msg.arg2 += 1

                val intent = Intent()
                intent.action = INTENT_ACTION

                intent.putExtra("Counter", msg.arg2)
                sendBroadcast(intent)

                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onCreate() {
        HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()

        serviceHandler!!.obtainMessage().also { msg ->
            msg.arg1 = startId
            msg.arg2 = 0
            serviceHandler!!.sendMessage(msg)
        }

        // restart, if service gets killed
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // returning null means that its bound to no activity
        return null
    }

    override fun onDestroy() {
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        val INTENT_ACTION: String = "Counter"
    }
}