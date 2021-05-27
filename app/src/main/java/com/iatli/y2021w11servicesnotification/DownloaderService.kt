package com.iatli.y2021w11servicesnotification

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class DownloaderService : Service() {
    val TAG = "CENG427"

    init {
        Log.d(TAG, "Service initiated. ")
    }
    // Mostly you don't need it
    override fun onBind(intent: Intent): IBinder ?{
        //TODO("Return the communication channel to the service.")

        return null
    }

    // This function  will be used to start the service from the activity
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val simulatedURL = intent?.getStringExtra("URL_KEY")

        simulatedURL?.let{
            Log.d(TAG, "Downloading URL: $simulatedURL")

            //simulates downloading. Will free UI
            Thread.sleep(5000)

            /*//Better way is using another thread
            Thread{
                Thread.sleep(5000)
            }.start()*/

            Log.d(TAG, "Download finished at $simulatedURL")

            //Broadcast the  result, user wants to know it
            Intent().also{
                it.setAction("DOWNLOAD_OPS")
                it.putExtra("DOWNLOAD_KEY", simulatedURL)
                //send result to activity
                sendBroadcast(it)
            }
        }

        //if RETURN_STICKY: Service keeps running
        // if START_NOT_STICKY: Service will be destroyed
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        //to see service is killed when you stop service
        Log.d(TAG, "Service is being destroyed")
    }
}