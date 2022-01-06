package com.ds.qqpega.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Paint

import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.PagerAdapter
import com.ds.qqpega.MainActivity
import com.ds.qqpega.dialog.OwnerDialog
import com.ds.qqpega.dialog.PhotoDialog
import com.ds.qqpega.R
import com.ds.qqpega.firebase.ListenerFB
import com.ds.qqpega.`object`.Places
import com.ds.qqpega.adapter.SliderAdapterDetails
import com.ds.qqpega.mask.Mask
import com.ds.qqpega.ui.places.PlacesFragment

import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_details3.*

import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {


    private val array = arrayOf(
            "Todos os horarios:",
            "Domingo: 00:00 as 00:00",
            "Segunda: 00:00 as 00:00",
            "Terça: 00:00 as 00:00",
            "Quarta: 00:00 as 00:00",
            "Quinta: 00:00 as 00:00",
            "Sexta: 00:00 as 00:00",
            "Sabado: 00:00 as 00:00"
    )

    var isOpen = false

    private val NEW_SPINNER_ID = 1
    // private var images: Array<String> = arrayOf("https://firebasestorage.googleapis.com/v0/b/qqpega-e9944.appspot.com/o/img_logo%2F3c1402be-77fa-4450-b8a2-61fb4c8c5a3e?alt=media&token=cbd6bc4c-96a9-4ba6-ad0f-21f765d33a49"
    //    ,"https://firebasestorage.googleapis.com/v0/b/qqpega-e9944.appspot.com/o/img_logo%2F3c1402be-77fa-4450-b8a2-61fb4c8c5a3e?alt=media&token=cbd6bc4c-96a9-4ba6-ad0f-21f765d33a49")


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details3)



        PhotoDialog.context = this
        back_details.setOnClickListener { finish() }

        //  preencherCampos()


        /*  imageView2.setOnClickListener({
              chamarDialogCustom()
          })*/
    }

    fun diff() {
        isOpen = false

        FirebaseFirestore.getInstance().collection("Places")
                .whereEqualTo("uf", ListenerFB.uf)
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        Log.w("ListenerFB", "listen:error", e)
                        return@addSnapshotListener
                    }



                    for (dc in snapshots!!.documentChanges) {
                        if (DocumentChange.Type.MODIFIED == dc.type) {
                            var aux = dc.document.toObject(Places::class.java)
                            for (i in PlacesFragment.lista.indices) {
                                if (place.name.equals(aux.name, true)) {
                                    place = aux
                                    preencherCampos()
                                }

                            }


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


    override fun onResume() {
        super.onResume()

        preencherCampos()
        diff()
        closeFab()

    }

    /*override fun onPause() {
        super.onPause()
        isOpen = false
    }*/


    fun preencherCampos() {
        var delimitador = "/mark/"
        var links = place.link_img_place.split(delimitador)
        images = links
        lista_photos.clear()
        for (i in 0..links.size - 2) {

            lista_photos.add(links[i])
        }

        /*setSupportActionBar(toolbar)
        supportActionBar?.apply {

            title = "Detalhes do local"
            subtitle = "Toolbar sub title"
            setDisplayShowHomeEnabled(true)
            setDisplayUseLogoEnabled(false)
        }*/

        img_cdb_detalhe.isVisible = false
        tv_nome_detalhe.text = place.name
        tv_promocoes2_detalhe.text = place.promocoes
        tv_atualizacao.text = "Data da ultima atualização " + place.atualizacao



        if (place.phone != "")
            tv_telefone2_detalhe.text = Mask.addMask(place.phone, "(##)####-####")

        if (place.celular != "")
            tv_celular2_detalhe.text = Mask.addMask(place.celular, "(##)#####-####")

        tv_endereco2_detalhe.text = place.address
        tv_tipo_detalhe.text = place.type
        // tv_celular2_detalhe.text= place.celular

        if (place.price.equals("")) {
            valor_entrada_detalhe.text = "Não paga entrada"
        } else
            valor_entrada_detalhe.text = "Entrada/Couver R$ " + place.price
        if (place.comidaDeButeco.equals("1", true)) {
            img_cdb_detalhe.isVisible = true
        }

        var adapter1: PagerAdapter = SliderAdapterDetails(this, lista_photos)
        Log.e("Details", "Links" + lista_photos.size)
        viewPager.adapter = adapter1
        var hoje = Date()
        var c = Calendar.getInstance()
        c.time = hoje
        var intHoje = c.get(Calendar.DAY_OF_WEEK)

        val ds = when (intHoje) {
            1 -> "DOM"
            2 -> "SEG"
            3 -> "TER"
            4 -> "QUA"
            5 -> "QUI"
            6 -> "SEX"
            7 -> "SAB"
            else -> "Invalido"
        }
        if (place.hs_domingo == place.he_domingo)
            array[1] = "Não funciona aos Domingos"
        else
            array[1] = "Domingo: " + place.hs_domingo + " as " + place.he_domingo
        if (place.hs_segunda == place.he_segunda) array[2] = "Não funciona as Segundas"
        else array[2] = "Segunda: " + place.hs_segunda + " as " + place.he_segunda
        if (place.hs_terca == place.he_terca) array[3] = "Não funciona as Terças"
        else array[3] = "Terça: " + place.hs_terca + " as " + place.he_terca
        if (place.hs_quarta == place.he_quarta) array[4] = "Não funciona as Quartas"
        else array[4] = "Quarta: " + place.hs_quarta + " as " + place.he_quarta
        if (place.hs_quinta == place.he_quinta) array[5] = "Não funciona as Quintas"
        else array[5] = "Quinta: " + place.hs_quinta + " as " + place.he_quinta
        if (place.hs_sexta == place.he_sexta) array[6] = "Não funciona as Sextas"
        else array[6] = "Sexta: " + place.hs_sexta + " as " + place.he_sexta
        if (place.hs_sabado == place.he_sabado) array[7] = "Não Funciona aos Sabados"
        else array[7] = "Sabado: " + place.hs_sabado + " as " + place.he_sabado
        if (ds.equals("DOM", true)) {
            if (place.hs_domingo == place.he_domingo) {
                tv_h0_detalhe.text = "Não funciona aos Domingos"

            } else
                tv_h0_detalhe.text =
                        "Abre as: " + place.hs_domingo + " e fecha as: " + place.he_domingo
        } else if (ds.equals("SEG", true)) {
            if (place.hs_segunda == place.he_segunda) {
                tv_h0_detalhe.text = "Não funciona as Segundas"
                //  tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text =
                        "Abre as: " + place.hs_segunda + " e fecha as: " + place.he_segunda
        } else if (ds.equals("TER", true)) {
            if (place.hs_terca == place.he_terca) {
                tv_h0_detalhe.text = "Não funciona as Terças"
                //  tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text = "Abre as: " + place.hs_terca + " e fecha as: " + place.he_terca
        } else if (ds.equals("QUA", true)) {
            if (place.hs_quarta == place.he_quarta) {
                tv_h0_detalhe.text = "Não funciona as Quartas"
                //  tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text =
                        "Abre as: " + place.hs_quarta + " e fecha as: " + place.he_quarta
        } else if (ds.equals("QUI", true)) {
            if (place.hs_quinta == place.he_quinta) {
                tv_h0_detalhe.text = "Não funciona as Quintas"
                //   tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text =
                        "Abre as: " + place.hs_quinta + " e fecha as: " + place.he_quinta
        } else if (ds.equals("SEX", true)) {
            if (place.hs_sexta == place.he_sexta) {
                tv_h0_detalhe.text = "Não funciona as Sextas"
                // tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text = "Abre as: " + place.hs_sexta + " e fecha as: " + place.he_sexta
        } else if (ds.equals("SAB", true)) {
            if (place.hs_sabado == place.he_sabado) {
                tv_h0_detalhe.text = "Não funciona aos sabados"
                //  tv_ambiente_detalhe.tv_h0_detalhe.setTextColor(Color.parseColor("#FF0000"))
            } else

                tv_h0_detalhe.text =
                        "Abre as: " + place.hs_sabado + " e fecha as: " + place.he_sabado
        }

      //  chk_open_area_detalhe.isClickable = false
      //  chk_close_area_detalhe.isClickable = false
       // chk_smoke_detalhe.isClickable = false
    //    chk_table_detalhe.isClickable = false
     //  chk_music_detalhe.isClickable = false
    //   chk_park_detalhe.isClickable = false
     //   chk_sinuca_detalhe.isClickable = false
    //    chk_play_ground_detalhe.isClickable = false
        chk_long_neck_detalhe.isClickable = false
        chk_botle_detalhe.isClickable = false
        chk_litrao_detalhe.isClickable = false
        chk_litrinho_detalhe.isClickable = false
        chk_lata_detalhe.isClickable = false
        chk_dose_detalhe.isClickable = false
        chk_drink_detalhe.isClickable = false
        chk_shot_detalhe.isClickable = false
        chk_beer_detalhe.isClickable = false
        chk_wyne_detalhe.isClickable = false
        chk_cerveja_artesanal_detalhe.isClickable = false
        chk_chopp_detalhe.isClickable = false
        chk_funk_detalhe.isClickable = false
        chk_sertanejo_detalhe.isClickable = false
        chk_pagode_detalhe.isClickable = false
        chk_forro_detalhe.isClickable = false
        chk_eletronica_detalhe.isClickable = false
        chk_rock_detalhe.isClickable = false
        chk_samba_detalhe.isClickable = false
        chk_pop_detalhe.isClickable = false
        chk_rap_detalhe.isClickable = false
        chk_hip_hop_detalhe.isClickable = false
        chk_jazz_detalhe.isClickable = false
        chk_sem_musica_detalhe.isClickable = false
        chk_espeto_detalhe.isClickable = false
        chk_porcao_detalhe.isClickable = false
        chk_self_service_detalhe.isClickable = false
        chk_a_la_carte_detalhe.isClickable = false
        chk_camiseta_detalhe.isClickable = false
        chk_bone_detalhe.isClickable = false
        chk_chinelo_detalhe.isClickable = false
        chk_bermuda_detalhe.isClickable = false
        chk_camisa_time_detalhe.isClickable = false



        if (!place.area_aberta.equals("1", true)) {
          //  chk_open_area_detalhe.isChecked = true
              tv_open_area_detalhe.apply {
                  paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

              }

        }else{
            tv_open_area_detalhe.apply {
                paintFlags = 0

            }

        }
        if (!place.area_fechada.equals("1", true)) {
           // chk_close_area_detalhe.isChecked = true
            tv_close_area_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_close_area_detalhe.apply {
                paintFlags = 0

            }
        }

        if (!place.area_fumante.equals("1", true)) {
         //   chk_smoke_detalhe.isChecked = true
            tv_smoke_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_smoke_detalhe.apply {
                paintFlags = 0

            }
        }

        if (!place.mesas.equals("1", true)) {
            //chk_table_detalhe.isChecked = true
            tv_table_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_table_detalhe.apply {
                paintFlags = 0

            }
        }

        if (!place.music.equals("1", true)) {
            //chk_music_detalhe.isChecked = true
            tv_music.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_music.apply {
                paintFlags = 0

            }
        }

        if (!place.park.equals("1", true)) {
            //chk_park_detalhe.isChecked = true
            tv_park_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_park_detalhe.apply {
                paintFlags = 0

            }
        }

        if (!place.sinuca.equals("1", true)) {
            //chk_sinuca_detalhe.isChecked = true
            tv_sinuca_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_sinuca_detalhe.apply {
                paintFlags = 0

            }
        }

        if (!place.play_ground.equals("1", true)) {
           // chk_play_ground_detalhe.isChecked = true
            tv_play_ground_detalhe.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            }
        }else{
            tv_play_ground_detalhe.apply {
                paintFlags = 0

            }
        }

        if (place.longNeck.equals("1", true)) {
            chk_long_neck_detalhe.isChecked = true
        }

        if (place.botle.equals("1", true)) {
            chk_botle_detalhe.isChecked = true
        }

        if (place.litrao.equals("1", true)) {
            chk_litrao_detalhe.isChecked = true
        }

        if (place.litrinho.equals("1", true)) {
            chk_litrinho_detalhe.isChecked = true
        }

        if (place.lata.equals("1", true)) {
            chk_lata_detalhe.isChecked = true
        }
        if (place.dose.equals("1", true)) {
            chk_dose_detalhe.isChecked = true
        }

        if (place.drink.equals("1", true)) {
            chk_drink_detalhe.isChecked = true
        }

        if (place.shot.equals("1", true)) {
            chk_shot_detalhe.isChecked = true
        }

        if (place.cerveja.equals("1", true)) {
            chk_beer_detalhe.isChecked = true
        }

        if (place.vinhos.equals("1", true)) {
            chk_wyne_detalhe.isChecked = true
        }

        if (place.cerveja_artsanal.equals("1", true)) {
            chk_cerveja_artesanal_detalhe.isChecked = true
        }

        if (place.chopp.equals("1", true)) {
            chk_chopp_detalhe.isChecked = true
        }

        if (place.funk.equals("1", true)) {
            chk_funk_detalhe.isChecked = true
        }

        if (place.sertanejo.equals("1", true)) {
            chk_sertanejo_detalhe.isChecked = true
        }

        if (place.pagode.equals("1", true)) {
            chk_pagode_detalhe.isChecked = true
        }

        if (place.forro.equals("1", true)) {
            chk_forro_detalhe.isChecked = true
        }

        if (place.eletronica.equals("1", true)) {
            chk_eletronica_detalhe.isChecked = true
        }

        if (place.rock.equals("1", true)) {
            chk_rock_detalhe.isChecked = true
        }

        if (place.samba.equals("1", true)) {
            chk_samba_detalhe.isChecked = true
        }

        if (place.pop.equals("1", true)) {
            chk_pop_detalhe.isChecked = true
        }

        if (place.rap.equals("1", true)) {
            chk_rap_detalhe.isChecked = true
        }

        if (place.hp.equals("1", true)) {
            chk_hip_hop_detalhe.isChecked = true
        }

        if (place.jazz.equals("1", true)) {
            chk_jazz_detalhe.isChecked = true
        }

        if (place.s_musica.equals("1", true)) {
            chk_sem_musica_detalhe.isChecked = true
        }

        if (place.espeto.equals("1", true)) {
            chk_espeto_detalhe.isChecked = true
        }

        if (place.porcao.equals("1", true)) {
            chk_porcao_detalhe.isChecked = true
        }

        if (place.self_Service.equals("1", true)) {
            chk_self_service_detalhe.isChecked = true
        }

        if (place.a_la_carte.equals("1", true)) {
            chk_a_la_carte_detalhe.isChecked = true
        }

        if (place.regata.equals("1", true)) {
            chk_camiseta_detalhe.isChecked = true
        }

        if (place.bone.equals("1", true)) {
            chk_bone_detalhe.isChecked = true
        }

        if (place.chinelo.equals("1", true)) {
            chk_chinelo_detalhe.isChecked = true
        }

        if (place.bermuda.equals("1", true)) {
            chk_bermuda_detalhe.isChecked = true
        }

        if (place.time.equals("1", true)) {
            chk_camisa_time_detalhe.isChecked = true
        }





        edit_latao_amstel_detalhe.text = "R$ " + place.amstel_l
        edit_long_neck_amstel_detalhe.text = "R$ " + place.amstel_ln
        edit_garrafa_amstel_detalhe.text = "R$ " + place.amstel_g

        edit_latao_brahma_detalhe.text = "R$ " + place.brahma_l
        edit_long_neck_brahma_detalhe.text = "R$ " + place.brahma_ln
        edit_garrafa_brahma_detalhe.text = "R$ " + place.brahma_g

        edit_latao_heineken_detalhe.text = "R$ " + place.heineken_l
        edit_long_neck_heineken_detalhe.text = "R$ " + place.heineken_ln
        edit_garrafa_heineken_detalhe.text = "R$ " + place.heineken_g

        edit_latao_budwiser_detalhe.text = "R$ " + place.bud_l
        edit_long_neck_budwiser_detalhe.text = "R$ " + place.bud_ln
        edit_garrafa_budwiser_detalhe.text = "R$ " + place.bud_g

        edit_latao_eisenbahn_detalhe.text = "R$ " + place.eisen_l
        edit_long_neck_eisenbahn_detalhe.text = "R$ " + place.eisen_ln
        edit_garrafa_eisenbahn_detalhe.text = "R$ " + place.eisen_g

        edit_latao_original_detalhe.text = "R$ " + place.original_l
        edit_long_neck_original_detalhe.text = "R$ " + place.original_ln
        edit_garrafa_original_detalhe.text = "R$ " + place.original_g

        edit_latao_bohemia_detalhe.text = "R$ " + place.bohemia_l
        edit_long_neck_bohemia_detalhe.text = "R$ " + place.bohemia_ln
        edit_garrafa_bohemia_detalhe.text = "R$ " + place.bohemia_g


        val fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabAntiClockWise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise)


        fabD1.hide()
        if (MainActivity.usuario!!.email.equals(place.owner, true)
                || MainActivity.usuario!!.email.equals("rodrigomdr@hotmail.com", true)
                || MainActivity.usuario!!.email.equals("r@gmail.com", true
                        || MainActivity.usuario!!.tagADM.equals("1"))) {

            fabD1.show()
            fabD1.setOnClickListener {
                if (isOpen) {
                    closeFab()

                } else {
                    fabD5.startAnimation(fabOpen)
                    fabD4.startAnimation(fabOpen)
                    fabD2.startAnimation(fabOpen)
                    fabD3.startAnimation(fabOpen)
                    fabD1.startAnimation(fabAntiClockWise)

                    fabD5.setOnClickListener { chamarDialogCustom() }
                    fabD2.setOnClickListener {
                        Log.e("Details FAB", "Camera")
                        val intent = Intent(it.context, UploadPhoto::class.java)
                        it.context.startActivity(intent)
                        closeFab()
                    }
                    fabD3.setOnClickListener {
                        EditActivity.place = place

                        val intent = Intent(it.context, EditActivity::class.java)

                        it.context.startActivity(intent)
                    }

                    isOpen = true
                }


            }
        }


        /* Picasso.get()
             .load("https://firebasestorage.googleapis.com/v0/b/qqpega-e9944.appspot.com/o/img_logo%2F3c1402be-77fa-4450-b8a2-61fb4c8c5a3e?alt=media&token=cbd6bc4c-96a9-4ba6-ad0f-21f765d33a49")
             .into(imageView2)

         Picasso.get()
             .load("https://firebasestorage.googleapis.com/v0/b/qqpega-e9944.appspot.com/o/img_logo%2F3c1402be-77fa-4450-b8a2-61fb4c8c5a3e?alt=media&token=cbd6bc4c-96a9-4ba6-ad0f-21f765d33a49")
             .into(imageView3)


         Picasso.get()
             .load("https://firebasestorage.googleapis.com/v0/b/qqpega-e9944.appspot.com/o/img_logo%2F3c1402be-77fa-4450-b8a2-61fb4c8c5a3e?alt=media&token=cbd6bc4c-96a9-4ba6-ad0f-21f765d33a49")
             .into(imageView4)*/
        if (place.link_logo != "") {
            Picasso.get()
                    .load(place.link_logo)
                    .into(logo_detalhe)
            //tv_detail_nome.text = place.name
            Log.e("Detail", "nome: " + place.name)
        }

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner_h) {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@DetailsActivity
        }
        var spin = Spinner(this)
        spin.id = NEW_SPINNER_ID
    }

    fun closeFab() {


        val fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close)
        val fabClockWise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)


        fabD2.startAnimation(fabClose)
        fabD3.startAnimation(fabClose)
        fabD4.startAnimation(fabClose)
        fabD5.startAnimation(fabClose)
        fabD1.startAnimation(fabClockWise)
        fabD2.isClickable = false
        fabD3.isClickable = false
        fabD4.isClickable = false
        isOpen = false


    }

    fun chamarDialog() {

        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alerta") // O Titulo da notificação
        alertDialog.setMessage("Pretende encerrar a Aplicação ?") // a mensagem ou alerta

        alertDialog.setPositiveButton("Sim", { _, _ ->

            //Aqui sera executado a instrução a sua escolha
            Toast.makeText(this, "Sim", Toast.LENGTH_LONG).show()

        })

        alertDialog.setNegativeButton("Não", { _, _ ->
            //Aqui sera executado a instrução a sua escolha
            Toast.makeText(this, "Não", Toast.LENGTH_LONG).show()

        })
        alertDialog.show()
    }

    /*  fun chamarDialogCustom() {

          var alertDialog: PhotoDialog = PhotoDialog()
          alertDialog.show(supportFragmentManager, "meu_dialog")

      }*/
    fun chamarDialogCustom() {

        var alertDialog: OwnerDialog = OwnerDialog()
        alertDialog.show(supportFragmentManager, "meu_dialog")


    }

    companion object {
        lateinit var place: Places
        lateinit var images: List<String>


        var lista_photos: ArrayList<String> = ArrayList()

        /*   fun chamarDialogCustom() {

               var alertDialog: PhotoDialog = PhotoDialog()
               alertDialog.show(FragmentManager, "meu_dialog")

           }*/
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


}