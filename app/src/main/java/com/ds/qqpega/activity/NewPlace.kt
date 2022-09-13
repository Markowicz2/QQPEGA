package com.ds.qqpega.activity


import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.ds.qqpega.R
import com.ds.qqpega.ui.maps.MapsFragment
import com.ds.qqpega.`object`.Places
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_new_pub.*


import java.text.SimpleDateFormat
import java.util.*

class NewPlace : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    companion object {
        private val IMAGE_local_PICK_CODE = 1000
        private val IMAGE_LOGO = 1005
        private val PERMISSION_CODE = 1001
        private lateinit var img: Intent
        private lateinit var imglogo: Intent
        private val array = arrayOf("MG", "SP", "RJ")
        private val NEW_SPINNER_ID = 1

        private var uf: String = "MG"
        private var mDatabase = FirebaseDatabase.getInstance()
        private var mDatabaseReference = mDatabase.reference.child("Places")
        private var mAuth = FirebaseAuth.getInstance()
        private var logo: Boolean = false
        private var img_p: Boolean = false
        private var ambiente = ""


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_new_pub)
        setContentView(R.layout.activity_new_pub)


        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, array)

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        with(spinner) {
            adapter = aa
            setSelection(0, false)
            onItemSelectedListener = this@NewPlace
        }
        var spin = Spinner(this)
        spin.id = NEW_SPINNER_ID


        radioGroup.setOnCheckedChangeListener { group, checkId ->
            if (checkId == R.id.rd_bar) {
                ambiente = rd_bar.text.toString()
                Log.e("new_Places", "Ambiente: " + ambiente)
            }
            if (checkId == R.id.rd_balada) {
                ambiente = rd_balada.text.toString()
                Log.e("new_Places", "Ambiente: " + ambiente)
            }
            if (checkId == R.id.rd_restaurante) {
                ambiente = rd_restaurante.text.toString()
                Log.e("new_Places", "Ambiente: " + ambiente)
            }
        }


        /* setSupportActionBar(toolbar_register)
         supportActionBar?.apply {

             title = "Cadastro novo local"

             setDisplayShowHomeEnabled(true)
             setDisplayUseLogoEnabled(false)
         }*/

        start_hour_segunda.setOnClickListener(this)
        end_hour_segunda.setOnClickListener(this)
        start_hour_terca.setOnClickListener(this)
        end_hour_terca.setOnClickListener(this)
        start_hour_quarta.setOnClickListener(this)
        end_hour_quarta.setOnClickListener(this)
        start_hour_quinta.setOnClickListener(this)
        end_hour_quinta.setOnClickListener(this)
        start_hour_sexta.setOnClickListener(this)
        end_hour_sexta.setOnClickListener(this)
        start_hour_sabado.setOnClickListener(this)
        end_hour_sabado.setOnClickListener(this)
        start_hour_domingo.setOnClickListener(this)
        end_hour_domingo.setOnClickListener(this)

        //bt_imagem_local.setOnClickListener(this)

        bt_logo.setOnClickListener(this)

        bt_cancel.setOnClickListener({ finish() })

        bt_send_place.setOnClickListener({
            createNewPlace()
        })

    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        // textView_msg!!.text = "Selected : "+ array[position]
        uf = array[position]
        Log.e("New_Place", "UF selecionada " + uf)
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, NewPlace.IMAGE_local_PICK_CODE)

    }

    private fun pickImageFromGalleryLogo() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, NewPlace.IMAGE_LOGO)

    }

    override fun onClick(p0: View) {
        val id = p0.id

        if (id == R.id.end_hour_segunda || id == R.id.start_hour_segunda) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_segunda)
                    end_hour_segunda.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_segunda)
                    start_hour_segunda.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_terca || id == R.id.start_hour_terca) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_terca)
                    end_hour_terca.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_terca)
                    start_hour_terca.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_quarta || id == R.id.start_hour_quarta) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_quarta)
                    end_hour_quarta.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_quarta)
                    start_hour_quarta.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_quinta || id == R.id.start_hour_quinta) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_quinta)
                    end_hour_quinta.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_quinta)
                    start_hour_quinta.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_sexta || id == R.id.start_hour_sexta) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_sexta)
                    end_hour_sexta.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_sexta)
                    start_hour_sexta.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_sabado || id == R.id.start_hour_sabado) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_sabado)
                    end_hour_sabado.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_sabado)
                    start_hour_sabado.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }else if (id == R.id.end_hour_domingo || id == R.id.start_hour_domingo) {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timerPiker, hour, minute ->

                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)


                if (id == R.id.end_hour_domingo)
                    end_hour_domingo.text = SimpleDateFormat("HH:mm").format(cal.time)
                else if (id == R.id.start_hour_domingo)
                    start_hour_domingo.text = SimpleDateFormat("HH:mm").format(cal.time)
            }



            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
        else if (id == R.id.bt_logo) {
            pickImageFromGalleryLogo()
        }
    }


    private fun createNewPlace() {
        var nome = name_place.text.toString()
        var endereco =
            street_place.text.toString() + " " + number_place.text.toString() + " " + neighborhood_place.text.toString() + " " + uf
        var rua = street_place.text.toString()
        var numero = number_place.text.toString()
        var bairro = neighborhood_place.text.toString()
        var telefone = phone_place.text.toString()
       // var h_abertura = start_hour.text.toString()
      //  var h_fechamento = end_hour.text.toString()
        var comida_buteco = if (chk_comida_buteco.isChecked) "1" else "0"
        var musica = if (chk_music.isChecked) "1" else "0"
        var estacionamento = if (chk_park.isChecked) "1" else "0"
        var drink = if (chk_drink.isChecked) "1" else "0"
        var long_neck = if (chk_long_neck.isChecked) "1" else "0"
        var garrafa = if (chk_botle.isChecked) "1" else "0"
        var espeto = if (chk_espeto.isChecked) "1" else "0"
        var porcao = if (chk_porcao.isChecked) "1" else "0"
        var self_service = if (chk_self_service.isChecked) "1" else "0"
        var hs_segunda = start_hour_segunda.text.toString()
        var he_segunda = end_hour_segunda.text.toString()
        var hs_terca = start_hour_terca.text.toString()
        var he_terca = end_hour_terca.text.toString()
        var hs_quarta = start_hour_quarta.text.toString()
        var he_quarta = end_hour_quarta.text.toString()
        var hs_quinta = start_hour_quinta.text.toString()
        var he_quinta = end_hour_quinta.text.toString()
        var hs_sexta = start_hour_sexta.text.toString()
        var he_sexta = end_hour_sexta.text.toString()
        var hs_sabado = start_hour_sabado.text.toString()
        var he_sabado = end_hour_sabado.text.toString()
        var hs_domingo = start_hour_domingo.text.toString()
        var he_domingo = end_hour_domingo.text.toString()
        var area_aberta = if (chk_open_area.isChecked) "1" else "0"
        var area_fechada = if (chk_close_area.isChecked) "1" else "0"
        var area_fumante = if (chk_smoke.isChecked) "1" else "0"
        var mesas = if (chk_table.isChecked) "1" else "0"
        var dose = if (chk_dose.isChecked) "1" else "0"
        var shot = if (chk_shot.isChecked) "1" else "0"
        var cerveja = if (chk_beer.isChecked) "1" else "0"
        var litrao = if (chk_litrao.isChecked) "1" else "0"
        var vinho = if (chk_wyne.isChecked) "1" else "0"
        var cerveja_artesanal = if (chk_cerveja_artesanal.isChecked) "1" else "0"
        var chopp = if (chk_chopp.isChecked) "1" else "0"
        var amstel_l = edit_latao_amstel.text.toString()
        var amsterl_ln = edit_long_neck_amstel.text.toString()
        var amstel_g = edit_garrafa_amstel.text.toString()
        var brahma_l = edit_latao_brahma.text.toString()
        var brahma_ln = edit_long_neck_brahma.text.toString()
        var brahma_g = edit_garrafa_brahma.text.toString()
        var heineken_l = edit_latao_heineken.text.toString()
        var heineken_ln = edit_long_neck_heineken.text.toString()
        var heineken_g = edit_garrafa_heineken.text.toString()
        var bud_l = edit_latao_budwiser.text.toString()
        var bud_ln = edit_long_neck_budwiser.text.toString()
        var bud_g = edit_garrafa_budwiser.text.toString()
        var eisenbahn_l = edit_latao_eisenbahn.text.toString()
        var eisenbahn_ln = edit_long_neck_eisenbahn.text.toString()
        var eisenbahn_g = edit_garrafa_eisenbahn.text.toString()
        var original_l = edit_latao_original.text.toString()
        var original_ln = edit_long_neck_original.text.toString()
        var original_g = edit_garrafa_original.text.toString()
        var bohemia_l = edit_latao_bohemia.text.toString()
        var bohemia_ln = edit_long_neck_bohemia.text.toString()
        var bohemia_g = edit_garrafa_bohemia.text.toString()
        var funk = if (chk_funk.isChecked) "1" else "0"
        var sertanejo = if (chk_sertanejo.isChecked) "1" else "0"
        var pagode = if (chk_pagode.isChecked) "1" else "0"
        var forro = if (chk_forro.isChecked) "1" else "0"
        var eletronica = if (chk_eletronica.isChecked) "1" else "0"
        var rock = if (chk_rock.isChecked) "1" else "0"
        var samba = if (chk_samba.isChecked) "1" else "0"
        var pop = if (chk_pop.isChecked) "1" else "0"
        var rap = if (chk_rap.isChecked) "1" else "0"
        var hp = if (chk_hip_hop.isChecked) "1" else "0"
        var jazz = if (chk_jazz.isChecked) "1" else "0"
        var s_musica = if (chk_sem_musica.isChecked) "1" else "0"
        var regata = if (chk_camiseta.isChecked) "1" else "0"
        var bone = if (chk_bone.isChecked) "1" else "0"
        var chinelo = if (chk_chinelo.isChecked) "1" else "0"
        var bermuda = if (chk_bermuda.isChecked) "1" else "0"
        var c_time = if (chk_camisa_time.isChecked) "1" else "0"
        var aLaCarte = if (chk_a_la_carte.isChecked) "1" else "0"
        var playGround = if (chk_play_ground.isChecked) "1" else "0"
        var lata = if (chk_lata.isChecked) "1" else "0"
        var litrinho = if (chk_litrinho.isChecked) "1" else "0"
        var celular = celphone_place.text.toString()
        var promocoes = ed_promocoes.text.toString()

        val date = Calendar.getInstance().time

        var dateTimeFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
       var atualizacao = dateTimeFormat.format(date)

        // var original_g = if (chk_segunda.isChecked) "1" else "0"*/

        var latitude =  MapsFragment.getLatLon(endereco,this)!!.get(0).latitude
        var longitude = MapsFragment.getLatLon(endereco,this)!!.get(0).longitude

        var valor = valor_entrada.text.toString()
        val selectedOption: Int = radioGroup.checkedRadioButtonId
        //TODO ambiente esta indo errado

        /*val ambiente = when (selectedOption) {
            1 -> "Bar"
            2 -> "Balada"
            3 -> "Restaurante"
            else -> "Invalido"
        }*/


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
            "",
            "",
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
            atualizacao
        )



        if (nome.isEmpty() || endereco.isEmpty() ) {
            Toast.makeText(this, "Favor preencher os campos corretamente", Toast.LENGTH_LONG)
                .show()
        } else {

            if (logo && !img_p)
                saveNewPlaceWithILogo(new_place)
            else if (img_p && !logo)
                saveNewPlaceWithIMG(new_place)
            else if (!logo && !img_p)
                saveNewPlace(new_place)
            else
                saveNewPlaceWithLogoAndIMG(new_place)


        }


    }

    fun saveNewPlaceWithLogoAndIMG(new_place: Places) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/img_places/" + filename)
        ref.putFile(img.data!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                new_place.link_img_place = it.toString()

                val ref2 = FirebaseStorage.getInstance().getReference("/img_logo/" + filename)
                ref2.putFile(imglogo.data!!).addOnSuccessListener {
                    ref2.downloadUrl.addOnSuccessListener {
                        new_place.link_logo = it.toString()
                        saveNewPlace(new_place)
                    }.addOnFailureListener({
                        Toast.makeText(this, "Erro na logo", Toast.LENGTH_LONG).show()
                    })


                }.addOnFailureListener({
                    Toast.makeText(this, "Erro na logo", Toast.LENGTH_LONG).show()
                })
            }.addOnFailureListener({
                Toast.makeText(this, "Erro na imagem do local", Toast.LENGTH_LONG).show()
            })


        }
    }

    fun saveNewPlaceWithIMG(new_place: Places) {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/img_places/" + filename)
        ref.putFile(img.data!!).addOnSuccessListener {
            ref.downloadUrl.addOnSuccessListener {
                new_place.link_img_place = it.toString()
                saveNewPlace(new_place)
            }.addOnFailureListener({
                Toast.makeText(this, "Erro na imagem do local", Toast.LENGTH_LONG).show()
            })


        }.addOnFailureListener({
            Toast.makeText(this, "Erro na imagem do local", Toast.LENGTH_LONG).show()
        })

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
            .document(new_place.name)
            .set(new_place)
            //.collection(ambiente)*/
            //.add(new_place)
            .addOnSuccessListener {
                Toast.makeText(this, "Local cadastrado com sucesso", Toast.LENGTH_LONG)
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
            NewPlace.PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else
                    Toast.makeText(this, "Permissao Negada", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Recebe a imagem vindo da galeria
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == NewPlace.IMAGE_local_PICK_CODE) {

            //Joga a imagem em um arquivo URI para enviar par ao firebase
            if (data != null) {
                NewPlace.img = data
                img_p = true
            }

            //Seta a imagem no image view
            /* img_place.setImageURI(data?.data)
             //torna o botao transparente
             bt_imagem_local.alpha = 0F*/
        } else if (resultCode == Activity.RESULT_OK && requestCode == NewPlace.IMAGE_LOGO) {
            if (data != null) {
                NewPlace.imglogo = data
                logo = true
            }

            //Seta a imagem no image view
            img_logo.setImageURI(data?.data)
            //torna o botao transparente
            bt_logo.alpha = 0F
        }
    }

}



