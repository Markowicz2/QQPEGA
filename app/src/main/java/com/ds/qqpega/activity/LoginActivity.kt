package com.ds.qqpega.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ds.qqpega.ui.maps.MapsFragment
import com.ds.qqpega.R
import com.ds.qqpega.activity.SplashActivity

import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private companion object{
        private val PERMISSION_CODE = 1002
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        bt_login.setOnClickListener{
            var email = edit_email_login.text.toString()
            var password = edit_password_login.text.toString()
            if( email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Favor preencher os campos",Toast.LENGTH_LONG).show()
            }else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        Toast.makeText(this,"Por favor aguarde...",Toast.LENGTH_LONG).show()
                        Thread.sleep(2000)
                        val intent = Intent(this, SplashActivity::class.java)

                        startActivity(intent)
                        finish()
                    }.addOnFailureListener{
                        Toast.makeText(this,"Usuário ou senha errados " +it.message,Toast.LENGTH_LONG).show()
                    }
            }


        }


        txt_account.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, SignIn::class.java)

            startActivity(intent)
        })



    }


}