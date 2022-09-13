package com.ds.qqpega.ui.places

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ds.qqpega.MainActivity
import com.ds.qqpega.R
import com.ds.qqpega.`object`.Places

import com.ds.qqpega.activity.NewPlace
import com.ds.qqpega.adapter.PlaceAdapter
import com.ds.qqpega.firebase.ListenerFB

import com.ds.qqpega.location.LocationClass

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A fragment representing a list of Items.
 */
class PlacesFragment : Fragment() {

    private var columnCount = 1
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var view1: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun diff() {


        FirebaseFirestore.getInstance().collection("Places")
            .whereEqualTo("uf", ListenerFB.uf)
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("ListenerFB", "listen:error", e)
                    return@addSnapshotListener
                }

                for (dc in snapshots!!.documentChanges) {

                    if (DocumentChange.Type.ADDED == dc.type) {
                        Log.e("PlacesFragmet","tamanho lista" + lista.size)
                        preencheLista()

                    }
                    if (DocumentChange.Type.MODIFIED == dc.type) {

                        preencheLista()

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

            }
    }

    fun addPlace() {
        val intent = Intent(context, NewPlace::class.java)
        startActivity(intent)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view1 = inflater.inflate(R.layout.fragment_place_list, container, false)


        preencheLista()
    //   diff()


        return view1
    }



    fun preencheLista() {
        viewManager = LinearLayoutManager(context)
        Log.e("PlacesFragmet","Tamanho da lista 2: " + lista.size)
        /* for(place in lista){
             val dec = DecimalFormat("#,#")
             place.distancia = dec.format(calculaDistancia(place.latitude,place.longitude, LocationClass.mLastLocation.latitude,
                 LocationClass.mLastLocation.longitude))

         }
         lista.sortBy { it.distancia }*/


        viewAdapter = PlaceAdapter(lista)
      //  PlacesFragment.lista.sortBy { it.aberto_fechado }
        recyclerView = view1.findViewById<RecyclerView>(R.id.lista).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }


        val mFab = view1.findViewById<FloatingActionButton>(R.id.addeventos)
        mFab.hide()

        //  Log.e("PlaceFragmer","ADMINISTRADOR" + uidUser)
        if (MainActivity.usuario != null)
            if (MainActivity.usuario!!.tagADM.equals("1")) {
                mFab.show()
                mFab.setOnClickListener({
                    addPlace()
                })
            }
    }


    companion object {

        var lista: MutableList<Places> = ArrayList()
        //var listaAux: MutableList<PlaceListAux> = ArrayList()


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