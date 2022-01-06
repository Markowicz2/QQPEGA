package com.ds.qqpega.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.viewpager.widget.PagerAdapter
import com.ds.qqpega.adapter.SliderAdapterPhotoUpload
import com.ds.qqpega.R

import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_upload_photo.*


import java.util.*
import kotlin.collections.ArrayList

class UploadPhoto : AppCompatActivity() {
    var PICK_IMAGE_MULTIPLE = 1
    var imageEncoded: String = ""
    var imagesEncodedList: ArrayList<Uri> = ArrayList()
    lateinit var adapter: PagerAdapter
    var imgUpload: ArrayList<Intent> = ArrayList()
    var linkImgs: String = ""
    var check = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_photo)

        var isOpen = false

        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabClockWise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
        val fabAntiClockWise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)





        fabM.setOnClickListener {
            if (isOpen) {
                fabCamera.startAnimation(fabClose)
                fabUpload.startAnimation(fabClose)
                fabM.startAnimation(fabClockWise)
                fabCamera.isClickable = false
                fabUpload.isClickable = false
                isOpen = false

            } else {
                fabCamera.startAnimation(fabOpen)
                fabUpload.startAnimation(fabOpen)
                fabM.startAnimation(fabAntiClockWise)

                fabCamera.setOnClickListener { pickImageFromGallery()  }
                fabUpload.setOnClickListener {upload() }

                isOpen = true
            }


        }


      //  val mFab = findViewById<FloatingActionButton>(R.id.fabCamera)

      //  mFab.setOnClickListener { pickImageFromGallery() }

       // bt_upload.setOnClickListener { upload() }
       // bt_cancelar_upload.setOnClickListener { finish() }
        back_photo_upload.setOnClickListener{finish()}

    }
    //TODO refresh da lista

    private fun addLinkImg() {

        DetailsActivity.place.link_img_place = linkImgs
   /*     FirebaseFirestore.getInstance().collection("Places")
            .document(DetailsActivity.place.name)
            .set(DetailsActivity.place)
            *//*.collection(ambiente)*//*
            .addOnSuccessListener {
                Toast.makeText(this, "Local editado com sucesso", Toast.LENGTH_LONG)
                    .show()

                finish()


            }
            .addOnFailureListener{
                Toast.makeText(this, "Local não cadastrado", Toast.LENGTH_LONG).show()
            }
*/


        FirebaseFirestore.getInstance().collection("Places")
            .document(DetailsActivity.place.name).update("link_img_place",linkImgs)
            .addOnSuccessListener { finish() }
    }

    private fun upload() {
        Toast.makeText(this, "Enviando foto, por favor aguarde", Toast.LENGTH_LONG).show()

        for (i in 0..imgUpload.size - 1) {

            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/img_places/" + filename)
            ref.putFile(imagesEncodedList[i]).addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener {
                    linkImgs = "" + it + "/mark/" + linkImgs

                    if (i == imgUpload.size - 1) {
                        addLinkImg()
                        DetailsActivity.lista_photos.clear()
                    }

                }.addOnFailureListener{
                    Toast.makeText(this, "Erro na imagem do local", Toast.LENGTH_LONG).show()
                }


            }
        }


    }

    private fun showImg() {

        var adapter: PagerAdapter = SliderAdapterPhotoUpload(this, imagesEncodedList)
        viewPagerUpload.adapter = adapter

    }


    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_MULTIPLE) {
            if (data?.getClipData() != null) {

                imagesEncodedList.clear()
                imgUpload.clear()
                var count = data.clipData!!.itemCount

                for (i in 0..count - 1) {

                    imgUpload.add(data)
                    var imageUri: Uri = data!!.clipData!!.getItemAt(i).uri


                    imagesEncodedList.add(imageUri)


                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                    Log.e("Details", "Pegou mais de uma imagem")
                }
                showImg()

            } else if (data?.getData() != null) {
                // if single image is selected
                imgUpload.add(data)
                var imageUri: Uri = data.data!!
                imagesEncodedList.add(imageUri)

                showImg()
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview
                Log.e("Details", "Pegou uma imagem")

            }
        }
    }
}