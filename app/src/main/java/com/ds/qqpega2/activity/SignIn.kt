package com.ds.qqpega.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ds.qqpega.ui.maps.MapsFragment
import com.ds.qqpega.R


import com.ds.qqpega.`object`.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.util.*


class SignIn : AppCompatActivity() {
    companion object{
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
        private lateinit var img : Intent

    }
    var mDatabase= FirebaseDatabase.getInstance()
    var mDatabaseReference = mDatabase.reference.child("Users")
    var mAuth = FirebaseAuth.getInstance()
    var img_ok = false






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        bt_photo_sign.setOnClickListener(View.OnClickListener { if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)

            }else{
                pickImageFromGallery()

            }


        }})

        bt_signin.setOnClickListener(View.OnClickListener {
            createNewAccount()
        })

        bt_sigin_cancel.setOnClickListener({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        })


    }

   private fun createNewAccount(){
    var nome = edit_nome_sign.text.toString()
    var email = edit_email_signin.text.toString()
    var password = edit_password_signin.text.toString()
       var newUser = Users(nome,"uid",email, "","0","0")

       if(nome.isEmpty() || email.isEmpty() || password.isEmpty()){
           Toast.makeText(this,"Favor preencher os campos",Toast.LENGTH_LONG).show()
       }else{
         mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){task ->
             if(task.isSuccessful){
                 newUser.uuid = task.result.user!!.uid
                 if(img_ok)
                saveUserInFireBase(newUser)
                    else
                     newUser(newUser)
                 Toast.makeText(this,"Usuario registrado com sucesso",Toast.LENGTH_LONG).show()

             }else{
                 Toast.makeText(this,"FALHA",Toast.LENGTH_LONG).show()
             }

         }
       }


    }

    private fun saveUserInFireBase(newUser: Users){
        val userId = mAuth.currentUser?.uid
        val currentUserDb = userId?.let { mDatabaseReference.child(it) }
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/img_profiles/"+filename)
        ref.putFile(img.data!!).addOnSuccessListener {

            ref.downloadUrl.addOnSuccessListener {
                newUser.profileUrl = it.toString()
                newUser(newUser)
              //  Log.e("SingIn",nome+uid+it.toString())


            }.addOnFailureListener({
                Log.e("SingIn","NoOK"+it.toString())
            })
        }.addOnFailureListener(){
            Toast.makeText(this,"FALHA FOTO",Toast.LENGTH_LONG).show()
            Log.e("SingIn","NoOK"+it.toString())
        }


        if (currentUserDb != null) {
            currentUserDb.child("name").setValue(edit_nome_sign.text.toString())
        }

    }

    private fun newUser(newUser: Users){
        FirebaseFirestore.getInstance().collection("Users")
            .document(newUser.uuid)
            .set(newUser)
            .addOnSuccessListener {
                Log.e("SingIn","OK")
                val intent = Intent(this, SplashActivity::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener({
                Log.e("SingIn","NoOK"+it.toString())
                Toast.makeText(this,"FALHA FOTO",Toast.LENGTH_LONG).show()
            })
    }

   private fun pickImageFromGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type="image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else
                    Toast.makeText(this,"Permissao Negada",Toast.LENGTH_LONG).show()
            }
        }
    }

//Recebe a imagem vindo da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){

            //Joga a imagem em um arquivo URI para enviar par ao firebase
            if (data != null) {
                img = data
            }

            //Seta a imagem no image view
            img_photo.setImageURI(data?.data)
            //torna o botao transparente
            bt_photo_sign.alpha = 0F
        }
    }

}