package com.ds.qqpega.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.ds.qqpega.MainActivity
import com.ds.qqpega.MainActivity.Companion.usuario
import com.ds.qqpega.R
import com.ds.qqpega.`object`.Users

import com.ds.qqpega.firebase.FirebaseClass
import com.ds.qqpega.firebase.ListenerFB
import com.ds.qqpega.location.LocationClass

import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.grpc.internal.LogExceptionRunnable
import java.util.*
import android.net.NetworkInfo

import android.net.ConnectivityManager
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.initialize


class SplashActivity : AppCompatActivity() {
    lateinit var listenerFb:ListenerFB
   companion object{
        val PERMISSION_CODE= 1002


       val permission2 = arrayOf(Manifest.permission.INTERNET,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        var checkPermissionOk = false
   }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

               requestPermissions(permission2, PERMISSION_CODE)
        }

        //TODO verificar internet e gps aqui



    }

    private fun time(){
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(p0: Long) {
                //TODO criar uma tela de progresso
            }
            override fun onFinish() {

                FirebaseClass.dbRef= Firebase.database.reference
                FirebaseClass.dbInstance= FirebaseFirestore.getInstance()
                listenerFb =  ListenerFB(com.ds.qqpega.firebase.FirebaseClass.dbInstance)
                Log.e("MAIN",""+FirebaseAuth.getInstance().uid)
                buscausuario()
                //fetchPlaces()
                listenerFb.runAll()

            }
        }
        timer.start()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){
            PERMISSION_CODE->{
                if(grantResults.size == 3)
                if( grantResults[0]== PackageManager.PERMISSION_GRANTED
                    &&grantResults[1]== PackageManager.PERMISSION_GRANTED
                    &&grantResults[2]== PackageManager.PERMISSION_GRANTED){
                    LocationClass.contex1 = this
                    LocationClass.startLocationUpdates()
                    FirebaseApp.initializeApp(applicationContext)
                    Log.e("Splash activity","UF: "+ ListenerFB.uf )
                    verifyAuthentication()
                   // time()


                }else {
                    Toast.makeText(this,"Favor aceitar as permissões",Toast.LENGTH_LONG).show()
                    requestPermissions(permission2, PERMISSION_CODE)
                    checkPermissionOk = true
                }
            }
        }

    }

    fun changeToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()

      }


 private fun buscausuario(){
        FirebaseFirestore.getInstance().collection("/Users")
            .document(FirebaseAuth.getInstance().uid!!)
            .get()
            .addOnSuccessListener {

                usuario   = it.toObject(Users::class.java)!!
                Log.e("MainActivity","Sou ADM? "+ usuario!!.tagADM + usuario!!.uuid)
                changeToLogin()

            }.addOnFailureListener({
                Log.e("MainActivit","falhao ao buscar usuario")
            })
    }

    private fun verifyAuthentication(){
        if(FirebaseAuth.getInstance().uid== null){
            val intent = Intent(this, LoginActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }else{
            time()
        }
    }
}