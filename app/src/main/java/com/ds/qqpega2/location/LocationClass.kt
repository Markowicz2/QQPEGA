package com.ds.qqpega.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.ds.qqpega.firebase.ListenerFB
import com.google.android.gms.location.*
import java.lang.Exception
import java.util.*

class LocationClass {
    companion object {
        lateinit var contex1: Context
        private val INTERVAL: Long = 2000
        private val FASTEST_INTERVAL: Long = 1000
        lateinit var mLastLocation: Location
        private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
        internal  var mLocationRequest: LocationRequest = LocationRequest()





        fun startLocationUpdates() {

            mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            mLocationRequest!!.setInterval(INTERVAL)
            mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

            // Create LocationSettingsRequest object using location request
            val builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(mLocationRequest!!)
            val locationSettingsRequest = builder.build()

            val settingsClient = LocationServices.getSettingsClient(contex1)
            settingsClient.checkLocationSettings(locationSettingsRequest)

            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contex1)
            // new Google API SDK v11 uses getFusedLocationProviderClient(this)
            if (ActivityCompat.checkSelfPermission(contex1, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(contex1, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                return
            }
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                Looper.myLooper())
        }

        private val mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {

                locationResult.lastLocation
                onLocationChanged(locationResult.lastLocation)
            }
        }

        fun onLocationChanged(location: Location) {


            try {

                mLastLocation = location
                Log.e("MAPS_fragment","Localizacao"+ mLastLocation.latitude)
                var geo: Geocoder = Geocoder(contex1, Locale.getDefault())
                // val geocoder = Geocoder(contex1)
                var adress = geo.getFromLocation(mLastLocation.latitude,mLastLocation.longitude,1)

                if(adress.get(0).adminArea.equals("Minas Gerais")){
                    ListenerFB.uf = "MG"
                }else if (adress.get(0).adminArea.equals("São Paulo")){
                    ListenerFB.uf = "SP"
                }
                Log.e("MAPS_fragment activity","Chegou aqui: "+ ListenerFB.uf )

            }catch (e: Exception){
                Log.e("LocationClass","Erro na localização: "+e)
            }

        }

        var teste: String =""
        fun stoplocationUpdates() {
            mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
        }







    }
}