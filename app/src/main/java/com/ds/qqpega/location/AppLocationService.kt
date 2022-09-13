package com.ds.qqpega.location

import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder

open class AppLocationService(context: Context) : Service(),
    LocationListener {
    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
}