package com.ds.qqpega.ui.maps

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.CountDownTimer

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ds.qqpega.R
import com.ds.qqpega.activity.DetailsActivity

import com.ds.qqpega.adapter.CustomInfoWindowAdapter

import com.ds.qqpega.firebase.ListenerFB
import com.ds.qqpega.location.LocationClass
import com.ds.qqpega.ui.places.PlacesFragment

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception
import java.time.LocalTime
import java.util.*

class MapsFragment : Fragment() {




    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        val beloHorizonte = LatLng(-19.8157, -43.9542)

        for (list in PlacesFragment.lista) {
            val aux = LatLng(list.latitude, list.longitude)
            var nome: String = list.name
            var snippet = list.distancia.toString()+"/mark/"+
                    list.price+"/mark/"+
                    list.link_logo+"/mark/"+
                    list.hs_domingo+"/mark/"+
                    list.he_domingo+"/mark/"+
                    list.hs_segunda+"/mark/"+
                    list.he_segunda+"/mark/"+
                    list.hs_terca+"/mark/"+
                    list.he_terca+"/mark/"+
                    list.hs_quarta+"/mark/"+
                    list.he_quarta+"/mark/"+
                    list.hs_quinta+"/mark/"+
                    list.he_quinta+"/mark/"+
                    list.hs_sexta+"/mark/"+
                    list.he_sexta+"/mark/"+
                    list.hs_sabado+"/mark/"+
                    list.he_sabado

            googleMap.addMarker(MarkerOptions().position(aux).title(nome).snippet(snippet)).setIcon(
                BitmapDescriptorFactory.fromResource(R.mipmap.pin))
            googleMap.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))



        }

        googleMap.setOnInfoWindowClickListener{
            it.title
            for(list in PlacesFragment.lista)
                if(list.name.equals(it.title,true)){
                    DetailsActivity.place = list
                    val intent = Intent(requireContext(), DetailsActivity::class.java)

                    requireContext().startActivity(intent)
                }

        }


        googleMap.isMyLocationEnabled = true


        val timer = object : CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {}
            override fun onFinish() {
                LocationClass.stoplocationUpdates()
            }
        }
        timer.start()

        var myLocation = LatLng(LocationClass.mLastLocation.latitude,LocationClass.mLastLocation.longitude)

        // googleMap.addMarker(MarkerOptions().
        //  position(beloHorizonte)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.pin_place))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 14f))

        /* var addresslist = geo.getFromLocationName("Rua dos expedicionarios 1170", 1)
         Log.e(
             "Maps_Fragment",
             "Teste de latitude e longitude: " + addresslist.get(0).latitude + " " + addresslist.get(
                 0
             ).longitude
         )
         Log.e(
             "Maps_Fragment",
             "Meu estado:  " + addresslist.get(0).adminArea + " " + addresslist.get(0).countryName
         )*/

        try {
            val dateFormat_hora =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime.now()
                } else {
                    TODO("VERSION.SDK_INT < O")
                }
            val changeHour = LocalTime.parse("18:00:00")
            val changeHour2 = LocalTime.parse("05:00:00")
            if(dateFormat_hora>=changeHour || dateFormat_hora <=changeHour2){

                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        context,
                        R.raw.dark
                    )
                )
            }else {

                googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                        context,
                        R.raw.silver
                    )
                )
            }




            /* if (!success) {
                 Log.e("MapsActivity", "Style parsing failed.")
             } else {
                 Log.e("MapsActivity", "Success")
             }*/
        } catch (e: Exception) {
            Log.e("MapsActivity", "Can't find style. Error: ", e)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?


        mapFragment?.getMapAsync(callback)


    }

    companion object {
        public fun getLatLon(address: String, context: Context): MutableList<Address>? {
            var geo: Geocoder = Geocoder(context, Locale.getDefault())

            var addresslist = geo.getFromLocationName(address, 1)
            return addresslist

        }
    }






}
