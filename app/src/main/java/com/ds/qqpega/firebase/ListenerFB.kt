package com.ds.qqpega.firebase

import android.util.Log
import android.view.View
import com.ds.qqpega.R
import com.ds.qqpega.`object`.Places
import com.ds.qqpega.location.LocationClass
import com.ds.qqpega.ui.places.PlacesFragment

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.time.LocalTime
import java.util.*
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class ListenerFB(var db: FirebaseFirestore) {



    companion object {

        var uf = ""

        private val EXECUTOR = ThreadPoolExecutor(
            2, 4,
            60, TimeUnit.SECONDS, LinkedBlockingQueue()
        )
    }

    internal fun runAll() {

        listenToDiffs()
    }

    //Busca os dados no FireBase
    private fun listenToDiffs() {
        Log.e("ListernerFB","UF: "+ uf)
        db.collection("Places")
            .whereEqualTo("uf", uf)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.e("ListenerFB", "listen:error", e)
                    return@addSnapshotListener
                }


                for (dc in snapshots!!.documentChanges) {
                    if (DocumentChange.Type.ADDED == dc.type) {
                        PlacesFragment.lista.add(dc.document.toObject(Places::class.java))
                        //  PlacesFragment.listaAux.add(dc.document.toObject(PlaceListAux::class.java))
                        Log.e("ListenerFB","ADD"+PlacesFragment.lista.size)
                    }
                    if (DocumentChange.Type.MODIFIED == dc.type) {
                        var aux = dc.document.toObject(Places::class.java)
                        for (i in PlacesFragment.lista.indices) {
                            if (PlacesFragment.lista.get(i).name.equals(aux.name, true)) {
                                PlacesFragment.lista.set(i, aux)
                              //  DetailsActivity.place = aux


                            }
                            Log.e("ListenerFB","MOD")
                        }


                        //  PlacesFragment.listaAux.add(dc.document.toObject(PlaceListAux::class.java))
                    }
                    if (DocumentChange.Type.REMOVED == dc.type) {
                        var aux = dc.document.toObject(Places::class.java)
                        for (i in PlacesFragment.lista.indices) {
                            if (PlacesFragment.lista.get(i).name.equals(aux.name, true)) {
                                PlacesFragment.lista.remove(aux)
                            }
                        }
                        Log.e("ListenerFB","removed")
                    }
                    when (dc.type) {

                        DocumentChange.Type.ADDED -> Log.d(
                            "ListenerFB",
                            "New city: ${dc.document.data}"
                        )
                        DocumentChange.Type.MODIFIED -> Log.d(
                            "ListenerFB",
                            "Modified city: ${dc.document.data}"
                        )
                        DocumentChange.Type.REMOVED -> Log.d(
                            "ListenerFB",
                            "Removed city: ${dc.document.data}"
                        )
                        //TODO Atualizar lista assim que modificar um local
                    }
                }
                Log.e("ListenerFB","Antes aberto fechadoLista tamanho" +PlacesFragment.lista.size)
                abertofechado()
                Log.e("ListenerFB"," Depois Lista tamanho" +PlacesFragment.lista.size)
                for(place in PlacesFragment.lista){
                    val dec = DecimalFormat("#.#")
                    var formart1 = calculaDistancia(place.latitude,place.longitude, LocationClass.mLastLocation.latitude,
                            LocationClass.mLastLocation.longitude)

                   var format2= dec.format(calculaDistancia(place.latitude,place.longitude, LocationClass.mLastLocation.latitude,
                            LocationClass.mLastLocation.longitude))+"f"
                    format2 = format2.replace(",",".",true)
                    place.distancia = format2.toFloat()

                }
                Log.e("ListenerFB"," Depois Lista tamanho" +PlacesFragment.lista.size)
                PlacesFragment.lista.sortBy { it.distancia }
                PlacesFragment.lista.sortByDescending { it.aberto_fechado }
            // Log.e("ListernerFB","Lista tamanho" +PlacesFragment.lista.size)

            }
        Log.e("ListenerFB","Lista tamanho depois" +PlacesFragment.lista.size)
        // [END listen_diffs]
    }
    fun getWeek(dia: Int): String? { //ex 07/03/2017
        var dayWeek = "---"
        when (dia) {
            1-> dayWeek = "DOM"
            2 -> dayWeek = "SEG"
            3 -> dayWeek = "TER"
            4 -> dayWeek = "QUA"
            5 -> dayWeek = "QUI"
            6 -> dayWeek = "SEX"
            7 -> dayWeek = "SAB"
        }
        Log.e("Place Adapter","dia da semana. "+ dia + dayWeek)

        return dayWeek
    }


    private fun abertofechado(){

        var hoje = Date()
        var c = Calendar.getInstance()
        c.time = hoje
        var intHoje = c.get(Calendar.DAY_OF_WEEK)

        var semana = getWeek(intHoje)

        val dateFormat_hora =
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    LocalTime.now()
                } else {
                    TODO("VERSION.SDK_INT < O")
                }

        for(place in PlacesFragment.lista) {
            val start_hs = LocalTime.parse(place.hs_segunda)
            val end_hs = LocalTime.parse(place.he_segunda)
            val start_ht = LocalTime.parse(place.hs_terca)
            val end_ht = LocalTime.parse(place.he_terca)
            val start_hq = LocalTime.parse(place.hs_quarta)
            val end_hq = LocalTime.parse(place.he_quarta)
            val start_hqi = LocalTime.parse(place.hs_quinta)
            val end_hqi = LocalTime.parse(place.he_quinta)
            val start_hse = LocalTime.parse(place.hs_sexta)
            val end_hse = LocalTime.parse(place.he_sexta)
            val start_hsa = LocalTime.parse(place.hs_sabado)
            val end_hsa = LocalTime.parse(place.he_sabado)
            val start_hd = LocalTime.parse(place.hs_domingo)
            val end_hd = LocalTime.parse(place.he_domingo)


            if (semana.equals("seg", true)) {
                if (end_hs > start_hs) {
                    if(dateFormat_hora < end_hd && end_hd < start_hd){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hs && dateFormat_hora < end_hs) {

                            place.aberto_fechado = 1

                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hs == end_hs) {
                    if(dateFormat_hora < end_hd && end_hd < start_hd){
                        place.aberto_fechado = 1
                    }else {

                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hd && end_hd < start_hd){
                        place.aberto_fechado = 1
                    }else {

                        if (dateFormat_hora > end_hs && dateFormat_hora < start_hs) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }
            } else if (semana.equals("ter", true)) {
                if (end_ht > start_ht) {
                    if(dateFormat_hora < end_hs && end_hs < start_hs){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_ht && dateFormat_hora < end_ht) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_ht == end_ht) {

                    if(dateFormat_hora < end_hs && end_hs < start_hs){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hs && end_hs < start_hs){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_ht && dateFormat_hora < start_ht) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            } else if (semana.equals("qua", true)) {
                if (end_hq > start_hq) {
                    if(dateFormat_hora < end_ht && end_ht < start_ht){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hq && dateFormat_hora < end_hq) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hq == end_hq) {

                    if(dateFormat_hora < end_ht && end_ht < start_ht){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_ht && end_ht < start_ht){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_hq && dateFormat_hora < start_hq) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            } else if (semana.equals("qui", true)) {
                if (end_hqi > start_hqi) {
                    if(dateFormat_hora < end_hq && end_hq < start_hq){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hqi && dateFormat_hora < end_hqi) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hqi == end_hqi) {
                    if(dateFormat_hora < end_hq && end_hq < start_hq){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hq && end_hq < start_hq){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_hqi && dateFormat_hora < start_hqi) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            } else if (semana.equals("sex", true)) {
                if (end_hse > start_hse) {
                    if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hse && dateFormat_hora < end_hse) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hse == end_hse) {
                    if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_hse && dateFormat_hora < start_hse) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            } else if (semana.equals("sab", true)) {
                if (end_hsa > start_hsa) {
                    if(dateFormat_hora < end_hse && end_hse < start_hse){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hsa && dateFormat_hora < end_hsa) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hsa == end_hsa) {
                    if(dateFormat_hora < end_hse && end_hse < start_hse){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hse && end_hse < start_hse){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_hsa && dateFormat_hora < start_hsa) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            } else if (semana.equals("dom", true)) {
                if (end_hd > start_hd) {
                    if(dateFormat_hora < end_hsa && end_hsa < start_hsa){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > start_hd && dateFormat_hora < end_hd) {

                            place.aberto_fechado = 1
                        } else {

                            place.aberto_fechado = 0
                        }
                    }
                } else if (start_hd == end_hd) {

                    if(dateFormat_hora < end_hsa && end_hsa < start_hsa){
                        place.aberto_fechado = 1
                    }else {
                        place.aberto_fechado = 0
                    }
                } else {
                    if(dateFormat_hora < end_hsa && end_hsa < start_hsa){
                        place.aberto_fechado = 1
                    }else {
                        if (dateFormat_hora > end_hd && dateFormat_hora < start_hd) {

                            place.aberto_fechado = 0

                        } else {

                            place.aberto_fechado = 1
                        }
                    }
                }

            }
            Log.e("ListenFB","TESTE" + place.aberto_fechado)
        }
        PlacesFragment.lista.sortedBy { it.aberto_fechado }
    }

    private fun calculaDistancia(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Double {
        //double earthRadius = 3958.75;//miles
        val earthRadius = 6371.0 //kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)
        val sindLat = Math.sin(dLat / 2)
        val sindLng = Math.sin(dLng / 2)
        val a = Math.pow(sindLat, 2.0) + (Math.pow(sindLng, 2.0)
                * Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)))
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        val dist = earthRadius * c
        return dist  //em Km
    }


}