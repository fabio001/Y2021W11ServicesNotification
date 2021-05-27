package com.iatli.y2021w11servicesnotification

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //register receiver for getting notification
        val intentFilter = IntentFilter()
        intentFilter.addAction("DOWNLOAD_OPS") //Should be the same action in service
        registerReceiver(MyReceiver(this), intentFilter)
    }

    fun stopService(view: View) {
        Intent(this, DownloaderService::class.java).also {
            stopService(it)
            val txt_status = findViewById<TextView>(R.id.txt_service_status)
            txt_status.text = "Service Status: Stopped"
        }

    }
    fun startService(view: View) {
        //create intent for stop service
        Intent(this, DownloaderService::class.java).also {
            startService(it)
            val txt_status = findViewById<TextView>(R.id.txt_service_status)
            txt_status.text = "Service Status: Running"
        }

    }
    fun sendData(view: View) {
        //Intent for sending data to service
        Intent(this, DownloaderService::class.java).also {
            //get data from edittext
            val edt_text = findViewById<EditText>(R.id.edt_data)
            val data = edt_text.text.toString()

            //add it to intent, lambda definition gives intent instance as "it"
            it.putExtra("URL_KEY", data)

            startService(it)
            val txt_status = findViewById<TextView>(R.id.txt_service_status)
            txt_status.text = "Service Status: Running"
        }
    }


}