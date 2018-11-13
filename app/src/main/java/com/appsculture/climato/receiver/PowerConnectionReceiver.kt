package com.appsculture.climato.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class PowerConnectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_POWER_CONNECTED == intent.action) {
            Toast.makeText(context, "Phone connected to power.", Toast.LENGTH_SHORT).show()
        }
    }
}