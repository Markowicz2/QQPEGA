package com.ds.qqpega.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ds.qqpega.R
import com.ds.qqpega.`object`.Places
import com.ds.qqpega.activity.DetailsActivity
import com.ds.qqpega.ui.places.PlacesFragment


import com.squareup.picasso.Picasso
import java.time.LocalTime
import java.util.*


class PlaceAdapter(
    private val values: MutableList<Places>
) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>(),View.OnClickListener {

    private var check_favorite = false
    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_place_item, parent, false)
           // .inflate(R.layout.teste_item_lista, parent, false)
        Log.e("PlaceAdapter","Lista?/ " + PlacesFragment.lista.size)
        return ViewHolder(view)
    }




    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        //joga dentro dos objetos da view os valores
        val item = values[position]
        holder.nome.text = item.name
        holder.valor.text = item.price



        holder.itemView.setOnClickListener {
            DetailsActivity.place = item
            val intent = Intent(it.context, DetailsActivity::class.java)

            it.context.startActivity(intent)
        }
        val dateFormat_hora =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalTime.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }



        holder.horario_local_f.visibility = View.INVISIBLE
        holder.horario_local_a.visibility = View.INVISIBLE

        var hoje = Date()
        var c = Calendar.getInstance()
        c.time = hoje
        var intHoje = c.get(Calendar.DAY_OF_WEEK)
        Log.e("Place Adaper "," Dia da semana"+ intHoje)
        var semana = getWeek(intHoje)

        val start_hs = LocalTime.parse(item.hs_segunda)
        val end_hs = LocalTime.parse(item.he_segunda)
        val start_ht = LocalTime.parse(item.hs_terca)
        val end_ht = LocalTime.parse(item.he_terca)
        val start_hq = LocalTime.parse(item.hs_quarta)
        val end_hq = LocalTime.parse(item.he_quarta)
        val start_hqi = LocalTime.parse(item.hs_quinta)
        val end_hqi = LocalTime.parse(item.he_quinta)
        val start_hse = LocalTime.parse(item.hs_sexta)
        val end_hse = LocalTime.parse(item.he_sexta)
        val start_hsa = LocalTime.parse(item.hs_sabado)
        val end_hsa = LocalTime.parse(item.he_sabado)
        val start_hd = LocalTime.parse(item.hs_domingo)
        val end_hd = LocalTime.parse(item.he_domingo)

        holder.promo.visibility = View.INVISIBLE
        if(item.promocoes != ""){
            holder.promo.visibility = View.VISIBLE
        }

        holder.img_favorite.setOnClickListener {

            if(check_favorite){
                holder.img_favorite.setImageResource(R.drawable.ic_favorite_border)
                check_favorite = false
            }else{
                check_favorite = true
            holder.img_favorite.setImageResource(R.drawable.ic_favorite)
                }
        }

      //  PlacesFragment.lista.sortBy { it.aberto_fechado }
      //  PlacesFragment.lista.sortBy { it.distancia }

        if(semana.equals("seg",true)) {
            if (end_hs > start_hs) {
                if(dateFormat_hora < end_hd && end_hd < start_hd){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_domingo
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hs && dateFormat_hora < end_hs) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_segunda
                        //item.aberto_fechado = "Aberto"
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_segunda
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hs == end_hs){
                if(dateFormat_hora < end_hd && end_hd < start_hd){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_domingo
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona as Segundas"


                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                    //item.aberto_fechado = "Fechado"
                }
            }
            else {
                if(dateFormat_hora < end_hd && end_hd < start_hd){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_domingo
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > end_hs && dateFormat_hora < start_hs) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_segunda
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_segunda
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)

                        //item.aberto_fechado = "Aberto"
                    }
                }
            }
        }else if(semana.equals("ter",true)){
            if (end_ht > start_ht) {
                if(dateFormat_hora < end_hs && end_hs < start_hs){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_segunda
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_ht && dateFormat_hora < end_ht) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_terca
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_terca
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_ht == end_ht){
                if(dateFormat_hora < end_hs && end_hs < start_hs){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_segunda
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona as Terças"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    //item.aberto_fechado = "Fechado"
                    holder.fundo_Item.alpha = 0.5f
                }
            }

            else {
                if(dateFormat_hora < end_hs && end_hs < start_hs){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_segunda
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > end_ht && dateFormat_hora < start_ht) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_terca
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        //item.aberto_fechado = "Fechado"
                        holder.fundo_Item.alpha = 0.5f

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_terca
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }else if(semana.equals("qua",true)){
            if (end_hq > start_hq) {
                if(dateFormat_hora < end_ht && end_ht < start_ht){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_terca
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hq && dateFormat_hora < end_hq) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_quarta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_quarta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hq == end_hq){
                if(dateFormat_hora < end_ht && end_ht < start_ht){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_terca
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona as Quartas"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                    //item.aberto_fechado = "Fechado"
                }
            }

            else {
                if(dateFormat_hora < end_ht && end_ht < start_ht){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_terca
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > end_hq && dateFormat_hora < start_hq) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_quarta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_quarta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }else if(semana.equals("qui",true)){
            if (end_hqi > start_hqi) {
                if(dateFormat_hora < end_hq && end_hq < start_hq){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quarta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hqi && dateFormat_hora < end_hqi) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_quinta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_quinta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hqi == end_hqi){
                if(dateFormat_hora < end_hq && end_hq < start_hq){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quarta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona as Quintas"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                    //item.aberto_fechado = "Fechado"
                }
            }

            else {
                if(dateFormat_hora < end_hq && end_hq < start_hq){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quarta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > end_hqi && dateFormat_hora < start_hqi) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_quinta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_quinta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }else if(semana.equals("sex",true)){
            if (end_hse > start_hse) {
                if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quinta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hse && dateFormat_hora < end_hse) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_sexta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_sexta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hse == end_hse){
                if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quinta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona as Sextas"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                    //item.aberto_fechado = "Fechado"
                }
            }

            else {
                if(dateFormat_hora < end_hqi && end_hqi < start_hqi){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_quinta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > end_hse && dateFormat_hora < start_hse) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_sexta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_sexta
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }else if(semana.equals("sab",true)){
            if (end_hsa > start_hsa) {
                if(dateFormat_hora < end_hse && end_hse < start_hse){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sexta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hsa && dateFormat_hora < end_hsa) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_sabado
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_sabado
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hsa == end_hsa){
                if(dateFormat_hora < end_hse && end_hse < start_hse){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sexta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona aos Sabados"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                    //item.aberto_fechado = "Fechado"
                }
            }

            else {
                if(dateFormat_hora < end_hse && end_hse < start_hse){
                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sexta
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }
                else {
                    if (dateFormat_hora > end_hsa && dateFormat_hora < start_hsa) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_sabado
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_sabado
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }else if(semana.equals("dom",true)){
            if (end_hd > start_hd) {
                if(dateFormat_hora < end_hsa && end_hsa < start_hsa){

                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sabado

                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    if (dateFormat_hora > start_hd && dateFormat_hora < end_hd) {
                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_domingo
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    } else {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as" + item.hs_domingo
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"
                    }
                }
            }else if(start_hd == end_hd){
                if(dateFormat_hora < end_hsa && end_hsa < start_hsa){

                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sabado

                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {
                    holder.horario_local_a.visibility = View.INVISIBLE
                    holder.horario_local_f.visibility = View.VISIBLE
                    holder.horario_local.text = "    Não funciona aos Domingos"
                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                    holder.fundo_Item.alpha = 0.5f
                }
                //item.aberto_fechado = "Fechado"
            }

            else {

                if(dateFormat_hora < end_hsa && end_hsa < start_hsa){

                    holder.horario_local_f.visibility = View.INVISIBLE
                    holder.horario_local_a.visibility = View.VISIBLE
                    holder.horario_local.text = " fecha as: " + item.he_sabado

                    holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                }else {

                    if (dateFormat_hora > end_hd && dateFormat_hora < start_hd) {
                        holder.horario_local_a.visibility = View.INVISIBLE
                        holder.horario_local_f.visibility = View.VISIBLE
                        holder.horario_local.text = "    abre as: " + item.hs_domingo
                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_close)
                        holder.fundo_Item.alpha = 0.5f
                        //item.aberto_fechado = "Fechado"

                    } else {

                        holder.horario_local_f.visibility = View.INVISIBLE
                        holder.horario_local_a.visibility = View.VISIBLE
                        holder.horario_local.text = " fecha as: " + item.he_domingo

                        holder.bg_logo.setBackgroundResource(R.drawable.bg_logo_open)
                        //item.aberto_fechado = "Aberto"
                    }
                }
            }

        }
     //  PlacesFragment.lista.sortWith(compareBy({it.distancia},{it.aberto_fechado}))


        if (item.link_logo != "")
            Picasso.get()
                .load(item.link_logo)
                .into(holder.img_logo)
        if(item.price.equals("")){
            holder.entrada.text = "Entrada/Couvert R$0,00"
        }else{
            holder.entrada.text = "Entrada/Couvert R$" + item.price
        }
      /*  val dec = DecimalFormat("#,#")
        var dist = dec.format(calculaDistancia(item.latitude,item.longitude,LocationClass.mLastLocation.latitude,
            LocationClass.mLastLocation.longitude))*/



        holder.distancia.text =item.distancia.toString() + " Km"

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //instancia os elementos da view
        /*val nome: TextView = view.findViewById(R.id.nome_place)
        val valor: TextView = view.findViewById(R.id.valor_entrada)
        val horario_local: TextView = view.findViewById(R.id.horario_local)
        val img_logo: ImageView = view.findViewById(R.id.img_logo_list)*/

        val nome: TextView = view.findViewById(R.id.nome_item)
        val valor: TextView = view.findViewById(R.id.entrada_item)
        val horario_local_a: TextView = view.findViewById(R.id.horario_a_item)
        val horario_local_f: TextView = view.findViewById(R.id.horario_f_item)
        val horario_local: TextView = view.findViewById(R.id.horario_item)
        val img_logo: ImageView = view.findViewById(R.id.img_logo_item)
        val entrada: TextView = view.findViewById(R.id.entrada_item)
        val distancia: TextView = view.findViewById(R.id.distancia_item)
        var fundo_Item: CardView = view.findViewById(R.id.fundo_item)
        var bg_logo: ImageView = view.findViewById(R.id.img_logo_bg)
        var promo: ImageView = view.findViewById(R.id.img_promocao)
        var img_favorite: ImageView = view.findViewById(R.id.img_favorite)




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



}
