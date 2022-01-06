package com.ds.qqpega.activity

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.ds.qqpega.R

import com.ds.qqpega.`object`.Places
import com.ds.qqpega.ui.maps.MapsFragment

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_place.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditActivity() : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {

    companion object {
        lateinit var place: Places
    }

    private val array = arrayOf("MG", "SP", "RJ")
    private var ambiente = ""
    private val EDIT_SPINNER_ID = 2
    private var uf = "MG"
    private val IMAGE_local_PICK_CODE = 1000
    private val PERMISSION_CODE = 1001
    private val IMAGE_LOGO = 1005
    private var logo: Boolean = false
    private lateinit var imglogo: Intent
    private var deteailsVar = DetailsActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_pub)
        setContentView(R.layout.activity_edit_place)


        name_place_edit.text = Editable.Factory.getInstance().newEditable(place.name)
        phone_place_edit.text = Editable.Factory.getInstance().newEditable(place.phone)
        celphone_place_edit.text = Editable.Factory.getInstance().newEditable(place.celular)
        street_place_edit.text = Editable.Factory.getInstance().newEditable(place.rua)
        number_place_edit.text = Editable.Factory.getInstance().newEditable(place.numero)
        neighborhood_place_edit.text = Editable.Factory.getInstance().newEditable(place.bairro)
        ed_promocoes_edit.text = Editable.Factory.getInstance().newEditable(place.promocoes)


        start_hour_segunda_edit.setOnClickListener(this)
        end_hour_segunda_edit.setOnClickListener(this)
        start_hour_terca_edit.setOnClickListener(this)
        end_hour_terca_edit.setOnClickListener(this)
        start_hour_quarta_edit.setOnClickListener(this)
        end_hour_quarta_edit.setOnClickListener(this)
        start_hour_quinta_edit.setOnClickListener(this)
        end_hour_quinta_edit.setOnClickListener(this)
        start_hour_sexta_edit.setOnClickListener(this)
        end_hour_sexta_edit.setOnClickListener(this)
        start_hour_sabado_edit.setOnClickListener(this)
        end_hour_sabado_edit.setOnClickListener(this)
        start_hour_domingo_edit.setOnClickListener(this)
        end_hour_domingo_edit.setOnClickListener(this)

        bt_logo_edit.setOnClickListener(this)

        bt_cancel_edit.setOnClickListener({ finish() })

        bt_send_place_edit.setOnClickListener({
            editPlace()
        })

        if (place.link_logo != "") {
            Picasso.get()
                .load(place.link_logo)
                .into(img_logo_edit)
            bt_logo_edit.alpha = 0F
        }

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)

        var position = when (place.uf) {
            "MG" -> 0
            "SP" -> 1
            "RJ" -> 2
            else -> 0
        }

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner_edit) {
            adapter = aa
            setSelection(position, false)
            onItemSelectedListener = this@EditActivity
        }
        var spin = Spinner(this)
        spin.id = EDIT_SPINNER_ID

        radioGroup_edit.setOnCheckedChangeListener { group, checkId ->
            if (checkId == R.id.rd_bar_edit) {
                ambiente = rd_bar_edit.text.toString()

            }
            if (checkId == R.id.rd_balada_edit) {
                ambiente = rd_balada_edit.text.toString()

            }
            if (checkId == R.id.rd_restaurante_edit) {
                ambiente = rd_restaurante_edit.text.toString()

            }
        }
        var type = when(place.type){
            "Bar" ->  rd_bar_edit.isChecked = true
            "Pub" -> rd_balada_edit.isChecked = true
            "Restaurante" -> rd_restaurante_edit.isChecked = true

            else-> ""
        }
        rd_bar_edit.isChecked = true
        start_hour_domingo_edit.text = place.hs_domingo
        end_hour_domingo_edit.text = place.he_domingo
        start_hour_segunda_edit.text = place.hs_segunda
        end_hour_segunda_edit.text = place.he_segunda
        start_hour_terca_edit.text = place.hs_terca
        end_hour_terca_edit.text = place.he_terca
        start_hour_quarta_edit.text = place.hs_quarta
        end_hour_quarta_edit.text = place.he_quarta
        start_hour_quinta_edit.text = place.hs_quinta
        end_hour_quinta_edit.text = place.he_quinta
        start_hour_sexta_edit.text = place.hs_sexta
        end_hour_sexta_edit.text = place.he_sexta
        start_hour_sabado_edit.text = place.hs_sabado
        end_hour_sabado_edit.text = place.he_sabado

        valor_entrada_edit.text = Editable.Factory.getInstance().newEditable(place.price)


        edit_latao_amstel_edit.text = Editable.Factory.getInstance().newEditable(place.amstel_l)
        edit_long_neck_amstel_edit.text =
            Editable.Factory.getInstance().newEditable(place.amstel_ln)
        edit_garrafa_amstel_edit.text = Editable.Factory.getInstance().newEditable(place.amstel_g)

        edit_latao_brahma_edit.text = Editable.Factory.getInstance().newEditable(place.brahma_l)
        edit_long_neck_brahma_edit.text =
            Editable.Factory.getInstance().newEditable(place.brahma_ln)
        edit_garrafa_brahma_edit.text = Editable.Factory.getInstance().newEditable(place.brahma_g)

        edit_latao_heineken_edit.text = Editable.Factory.getInstance().newEditable(place.heineken_l)
        edit_long_neck_heineken_edit.text =
            Editable.Factory.getInstance().newEditable(place.heineken_ln)
        edit_garrafa_heineken_edit.text =
            Editable.Factory.getInstance().newEditable(place.heineken_g)

        edit_latao_budwiser_edit.text = Editable.Factory.getInstance().newEditable(place.bud_l)
        edit_long_neck_budwiser_edit.text = Editable.Factory.getInstance().newEditable(place.bud_ln)
        edit_garrafa_budwiser_edit.text = Editable.Factory.getInstance().newEditable(place.bud_g)

        edit_latao_eisenbahn_edit.text = Editable.Factory.getInstance().newEditable(place.eisen_l)
        edit_long_neck_eisenbahn_edit.text =
            Editable.Factory.getInstance().newEditable(place.eisen_ln)
        edit_garrafa_eisenbahn_edit.text = Editable.Factory.getInstance().newEditable(place.eisen_g)

        edit_latao_original_edit.text = Editable.Factory.getInstance().newEditable(place.original_l)
        edit_long_neck_original_edit.text =
            Editable.Factory.getInstance().newEditable(place.original_ln)
        edit_garrafa_original_edit.text =
            Editable.Factory.getInstance().newEditable(place.original_g)

        edit_latao_bohemia_edit.text = Editable.Factory.getInstance().newEditable(place.bohemia_l)
        edit_long_neck_bohemia_edit.text =
            Editable.Factory.getInstance().newEditable(place.bohemia_ln)
        edit_garrafa_bohemia_edit.text = Editable.Factory.getInstance().newEditable(place.bohemia_g)

        if (place.area_aberta.equals("1", true)) {
            chk_open_area_edit.isChecked = true
        }
        if (place.area_fechada.equals("1", true)) {
            chk_close_area_edit.isChecked = true
        }

        if (place.area_fumante.equals("1", true)) {
            chk_smoke_edit.isChecked = true
        }

        if (place.mesas.equals("1", true)) {
            chk_table_edit.isChecked = true
        }

        if (place.music.equals("1", true)) {
            chk_music_edit.isChecked = true
        }

        if (place.park.equals("1", true)) {
            chk_park_edit.isChecked = true
        }

        if (place.sinuca.equals("1", true)) {
            chk_sinuca_edit.isChecked = true
        }

        if (place.play_ground.equals("1", true)) {
            chk_play_ground_edit.isChecked = true
        }

        if (place.longNeck.equals("1", true)) {
            chk_long_neck_edit.isChecked = true
        }

        if (place.botle.equals("1", true)) {
            chk_botle_edit.isChecked = true
        }

        if (place.litrao.equals("1", true)) {
            chk_litrao_edit.isChecked = true
        }

        if (place.litrinho.equals("1", true)) {
            chk_litrinho_edit.isChecked = true
        }

        if (place.lata.equals("1", true)) {
            chk_lata_edit.isChecked = true
        }
        if (place.dose.equals("1", true)) {
            chk_dose_edit.isChecked = true
        }

        if (place.drink.equals("1", true)) {
            chk_drink_edit.isChecked = true
        }

        if (place.shot.equals("1", true)) {
            chk_shot_edit.isChecked = true
        }

        if (place.cerveja.equals("1", true)) {
            chk_beer_edit.isChecked = true
        }

        if (place.vinhos.equals("1", true)) {
            chk_wyne_edit.isChecked = true
        }

        if (place.cerveja_artsanal.equals("1", true)) {
            chk_cerveja_artesanal_edit.isChecked = true
        }

        if (place.chopp.equals("1", true)) {
            chk_chopp_edit.isChecked = true
        }

        if (place.funk.equals("1", true)) {
            chk_funk_edit.isChecked = true
        }

        if (place.sertanejo.equals("1", true)) {
            chk_sertanejo_edit.isChecked = true
        }

        if (place.pagode.equals("1", true)) {
            chk_pagode_edit.isChecked = true
        }

        if (place.forro.equals("1", true)) {
            chk_forro_edit.isChecked = true
        }

        if (place.eletronica.equals("1", true)) {
            chk_eletronica_edit.isChecked = true
        }

        if (place.rock.equals("1", true)) {
            chk_rock_edit.isChecked = true
        }

        if (place.samba.equals("1", true)) {
            chk_samba_edit.isChecked = true
        }

        if (place.pop.equals("1", true)) {
            chk_pop_edit.isChecked = true
        }

        if (place.rap.equals("1", true)) {
            chk_rap_edit.isChecked = true
        }

        if (place.hp.equals("1", true)) {
            chk_hip_hop_edit.isChecked = true
        }

        if (place.jazz.equals("1", true)) {
            chk_jazz_edit.isChecked = true
        }

        if (place.s_musica.equals("1", true)) {
            chk_sem_musica_edit.isChecked = true
        }

        if (place.espeto.equals("1", true)) {
            chk_espeto_edit.isChecked = true
        }

        if (place.porcao.equals("1", true)) {
            chk_porcao_edit.isChecked = true
        }

        if (place.self_Service.equals("1", true)) {
            chk_self_service_edit.isChecked = true
        }

        if (place.a_la_carte.equals("1", true)) {
            chk_a_la_carte_edit.isChecked = true
        }

        if (place.regata.equals("1", true)) {
            chk_camiseta_edit.isChecked = true
        }

        if (place.bone.equals("1", true)) {
            chk_bone_edit.isChecked = true
        }

        if (place.chinelo.equals("1", true)) {
            chk_chinelo_edit.isChecked = true
        }

        if (place.bermuda.equals("1", true)) {
            chk_bermuda_edit.isChecked = true
        }

        if (place.time.equals("1", true)) {
            chk_camisa_time_edit.isChecked = true
        }
        if (place.comidaDeButeco.equals("1", true)) {
            chk_comida_buteco_edit.isChecked = true
        }


    }

    private fun pickImageFromGalleryLogo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_LOGO)

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // textView_msg!!.text = "Selected : "+ array[position]
        uf = array[position]
        Log.e("New_Place", "UF selecionada " + uf)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }


    override fun onClick(p0: View) {
        val id = p0.id

        if (id == R.id.end_hour_segunda_edit || id == R.id.start_hour_segunda_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_segunda_edit)
                    end_hour_segunda_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_segunda_edit)
                    start_hour_segunda_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_terca_edit || id == R.id.start_hour_terca_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_terca_edit)
                    end_hour_terca_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_terca_edit)
                    start_hour_terca_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_quarta_edit || id == R.id.start_hour_quarta_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_quarta_edit)
                    end_hour_quarta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_quarta_edit)
                    start_hour_quarta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_quinta_edit || id == R.id.start_hour_quinta_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_quinta_edit)
                    end_hour_quinta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_quinta_edit)
                    start_hour_quinta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_sexta_edit || id == R.id.start_hour_sexta_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_sexta_edit)
                    end_hour_sexta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_sexta_edit)
                    start_hour_sexta_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_sabado_edit || id == R.id.start_hour_sabado_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_sabado_edit)
                    end_hour_sabado_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_sabado_edit)
                    start_hour_sabado_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.end_hour_domingo_edit || id == R.id.start_hour_domingo_edit) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_domingo_edit)
                    end_hour_domingo_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_domingo_edit)
                    start_hour_domingo_edit.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        } else if (id == R.id.bt_logo_edit) {
            pickImageFromGalleryLogo()
        }
    }


    private fun editPlace() {
        var nome = name_place_edit.text.toString()
        var endereco =
            street_place_edit.text.toString() + " " + number_place_edit.text.toString() + " " + neighborhood_place_edit.text.toString() + " " + uf
        var rua = street_place_edit.text.toString()
        var numero = number_place_edit.text.toString()
        var bairro = neighborhood_place_edit.text.toString()
        var telefone = phone_place_edit.text.toString()
        var celular = celphone_place_edit.text.toString()

        // var h_abertura = start_hour.text.toString()
        //  var h_fechamento = end_hour.text.toString()
        var comida_buteco = if (chk_comida_buteco_edit.isChecked) "1" else "0"
        var musica = if (chk_music_edit.isChecked) "1" else "0"
        var estacionamento = if (chk_park_edit.isChecked) "1" else "0"
        var drink = if (chk_drink_edit.isChecked) "1" else "0"
        var long_neck = if (chk_long_neck_edit.isChecked) "1" else "0"
        var garrafa = if (chk_botle_edit.isChecked) "1" else "0"
        var espeto = if (chk_espeto_edit.isChecked) "1" else "0"
        var porcao = if (chk_porcao_edit.isChecked) "1" else "0"
        var self_service = if (chk_self_service_edit.isChecked) "1" else "0"
        var hs_segunda = start_hour_segunda_edit.text.toString()
        var he_segunda = end_hour_segunda_edit.text.toString()
        var hs_terca = start_hour_terca_edit.text.toString()
        var he_terca = end_hour_terca_edit.text.toString()
        var hs_quarta = start_hour_quarta_edit.text.toString()
        var he_quarta = end_hour_quarta_edit.text.toString()
        var hs_quinta = start_hour_quinta_edit.text.toString()
        var he_quinta = end_hour_quinta_edit.text.toString()
        var hs_sexta = start_hour_sexta_edit.text.toString()
        var he_sexta = end_hour_sexta_edit.text.toString()
        var hs_sabado = start_hour_sabado_edit.text.toString()
        var he_sabado = end_hour_sabado_edit.text.toString()
        var hs_domingo = start_hour_domingo_edit.text.toString()
        var he_domingo = end_hour_domingo_edit.text.toString()
        var area_aberta = if (chk_open_area_edit.isChecked) "1" else "0"
        var area_fechada = if (chk_close_area_edit.isChecked) "1" else "0"
        var area_fumante = if (chk_smoke_edit.isChecked) "1" else "0"
        var mesas = if (chk_table_edit.isChecked) "1" else "0"
        var dose = if (chk_dose_edit.isChecked) "1" else "0"
        var shot = if (chk_shot_edit.isChecked) "1" else "0"
        var cerveja = if (chk_beer_edit.isChecked) "1" else "0"
        var litrao = if (chk_litrao_edit.isChecked) "1" else "0"
        var vinho = if (chk_wyne_edit.isChecked) "1" else "0"
        var cerveja_artesanal = if (chk_cerveja_artesanal_edit.isChecked) "1" else "0"
        var chopp = if (chk_chopp_edit.isChecked) "1" else "0"
        var amstel_l = edit_latao_amstel_edit.text.toString()
        var amsterl_ln = edit_long_neck_amstel_edit.text.toString()
        var amstel_g = edit_garrafa_amstel_edit.text.toString()
        var brahma_l = edit_latao_brahma_edit.text.toString()
        var brahma_ln = edit_long_neck_brahma_edit.text.toString()
        var brahma_g = edit_garrafa_brahma_edit.text.toString()
        var heineken_l = edit_latao_heineken_edit.text.toString()
        var heineken_ln = edit_long_neck_heineken_edit.text.toString()
        var heineken_g = edit_garrafa_heineken_edit.text.toString()
        var bud_l = edit_latao_budwiser_edit.text.toString()
        var bud_ln = edit_long_neck_budwiser_edit.text.toString()
        var bud_g = edit_garrafa_budwiser_edit.text.toString()
        var eisenbahn_l = edit_latao_eisenbahn_edit.text.toString()
        var eisenbahn_ln = edit_long_neck_eisenbahn_edit.text.toString()
        var eisenbahn_g = edit_garrafa_eisenbahn_edit.text.toString()
        var original_l = edit_latao_original_edit.text.toString()
        var original_ln = edit_long_neck_original_edit.text.toString()
        var original_g = edit_garrafa_original_edit.text.toString()
        var bohemia_l = edit_latao_bohemia_edit.text.toString()
        var bohemia_ln = edit_long_neck_bohemia_edit.text.toString()
        var bohemia_g = edit_garrafa_bohemia_edit.text.toString()
        var funk = if (chk_funk_edit.isChecked) "1" else "0"
        var sertanejo = if (chk_sertanejo_edit.isChecked) "1" else "0"
        var pagode = if (chk_pagode_edit.isChecked) "1" else "0"
        var forro = if (chk_forro_edit.isChecked) "1" else "0"
        var eletronica = if (chk_eletronica_edit.isChecked) "1" else "0"
        var rock = if (chk_rock_edit.isChecked) "1" else "0"
        var samba = if (chk_samba_edit.isChecked) "1" else "0"
        var pop = if (chk_pop_edit.isChecked) "1" else "0"
        var rap = if (chk_rap_edit.isChecked) "1" else "0"
        var hp = if (chk_hip_hop_edit.isChecked) "1" else "0"
        var jazz = if (chk_jazz_edit.isChecked) "1" else "0"
        var s_musica = if (chk_sem_musica_edit.isChecked) "1" else "0"
        var regata = if (chk_camiseta_edit.isChecked) "1" else "0"
        var bone = if (chk_bone_edit.isChecked) "1" else "0"
        var chinelo = if (chk_chinelo_edit.isChecked) "1" else "0"
        var bermuda = if (chk_bermuda_edit.isChecked) "1" else "0"
        var c_time = if (chk_camisa_time_edit.isChecked) "1" else "0"
        var aLaCarte = if (chk_a_la_carte_edit.isChecked) "1" else "0"
        var playGround = if (chk_play_ground_edit.isChecked) "1" else "0"
        var lata = if (chk_lata_edit.isChecked) "1" else "0"
        var litrinho = if (chk_litrinho_edit.isChecked) "1" else "0"
        var promocoes = ed_promocoes_edit.text.toString()
        var sinuca = if (chk_sinuca_edit.isChecked) "1" else "0"

        // var original_g = if (chk_segunda.isChecked) "1" else "0"*/
        val date = Calendar.getInstance().time

        var dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var atualizacao = dateTimeFormat.format(date)

        var latitude = MapsFragment.getLatLon(endereco, this)!!.get(0).latitude
        var longitude = MapsFragment.getLatLon(endereco, this)!!.get(0).longitude

        var valor = valor_entrada_edit.text.toString()
       // val selectedOption: Int = radioGroup_edit.checkedRadioButtonId


        /*val ambiente = when (selectedOption) {
            1 -> "Bar"
            2 -> "Balada"
            3 -> "Restaurante"
            else -> "Invalido"
        }*/

        var linkArray: ArrayList<String> = ArrayList()

        var new_place = Places(
            endereco,
            area_aberta,
            area_fechada,
            area_fumante,
            mesas,
            dose,
            shot,
            vinho,
            cerveja_artesanal,
            cerveja,
            chopp,
            amstel_l,
            amsterl_ln,
            amstel_g,
            brahma_l,
            brahma_ln,
            brahma_g,
            heineken_l,
            heineken_ln,
            heineken_g,
            bud_l,
            bud_ln,
            bud_g,
            eisenbahn_l,
            eisenbahn_ln,
            eisenbahn_g,
            original_l,
            original_ln,
            original_g,
            bohemia_l,
            bohemia_ln,
            bohemia_g,
            funk,
            sertanejo,
            pagode,
            forro,
            eletronica,
            rock,
            samba,
            pop,
            rap,
            hp,
            jazz,
            s_musica,
            regata,
            bone,
            chinelo,
            bermuda,
            c_time,
            aLaCarte,
            hs_segunda,
            he_segunda,
            hs_terca,
            he_terca,
            hs_quarta,
            he_quarta,
            hs_quinta,
            he_quinta,
            hs_sexta,
            he_sexta,
            hs_sabado,
            he_sabado,
            hs_domingo,
            he_domingo,
            garrafa,
            litrao,
            comida_buteco,
            drink,
            espeto,
            place.link_logo,
            place.link_img_place,
            long_neck,
            musica,
            nome,
            estacionamento,
            telefone,
            porcao,
            valor,
            self_service,
           uf,
            ambiente,
            latitude,
            longitude,
            "",
            0F,
            lata,
            litrinho,
            playGround,
            rua,
            numero,
            bairro,
            celular,
            promocoes,
            0,
            atualizacao,
            sinuca
        )



        if (nome.isEmpty() || endereco.isEmpty()) {
            Toast.makeText(this, "Favor preencher os campos corretamente", Toast.LENGTH_LONG)
                .show()
        } else {

            if (logo)
                saveNewPlaceWithILogo(new_place)
            else
                saveNewPlace(new_place)


        }


    }

    fun saveNewPlaceWithILogo(new_place: Places) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/img_logo/" + filename)
        ref.putFile(imglogo.data!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                new_place.link_logo = it.toString()
                saveNewPlace(new_place)
            }.addOnFailureListener({
                Toast.makeText(this, "Erro na imagem da logo", Toast.LENGTH_LONG).show()
            })
        }.addOnFailureListener({
            Toast.makeText(this, "Erro na imagem do local", Toast.LENGTH_LONG).show()
        })

    }


    fun saveNewPlace(new_place: Places) {

        FirebaseFirestore.getInstance().collection("Places")
            .document(place.name)
            .set(new_place)
            /*.collection(ambiente)*/
            .addOnSuccessListener {
                Toast.makeText(this, "Local editado com sucesso", Toast.LENGTH_LONG)
                    .show()



                finish()


            }
            .addOnFailureListener({
                Toast.makeText(this, "Local não cadastrado", Toast.LENGTH_LONG).show()
            })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGalleryLogo()
                } else
                    Toast.makeText(this, "Permissao Negada", Toast.LENGTH_LONG).show()
            }
        }
    }


    //Recebe a imagem vindo da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_LOGO) {
            if (data != null) {
              imglogo = data
                logo = true
            }

            //Seta a imagem no image view
            img_logo_edit.setImageURI(data?.data)
            //torna o botao transparente
            bt_logo_edit.alpha = 0F
        }
    }
}




