package com.ds.qqpega.adapter

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.ds.qqpega.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.squareup.picasso.Picasso
import java.time.LocalTime
import java.util.*

class CustomInfoWindowAdapter(context: Context) : GoogleMap.InfoWindowAdapter {
    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window_marker, null)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun rendowWindowText(marker: Marker, view: View) {
        var tvNome = view.findViewById<TextView>(R.id.nome_window)
        val horario_local_a = view.findViewById<TextView>(R.id.horario_a_window)
        val horario_local_f = view.findViewById<TextView>(R.id.horario_f_window)
        val horario_local = view.findViewById<TextView>(R.id.horario_window)
        val tvDistancia = view.findViewById<TextView>(R.id.distancia_window)
        val imgLogo = view.findViewById<ImageView>(R.id.img_logo_window)
        val tvEntrada = view.findViewById<TextView>(R.id.entrada_window)
        val fundo_Item = view.findViewById<RelativeLayout>(R.id.fundo_item_window)
        var delimitador = "/mark/"
        var parts= marker.snippet.split(delimitador)

        Log.e("Window Adapter","PARTS: " + parts)

        val start_hd = LocalTime.parse(parts[3])
        val end_hd = LocalTime.parse(parts[4])
        val start_hs = LocalTime.parse(parts[5])
        val end_hs = LocalTime.parse(parts[6])
        val start_ht = LocalTime.parse(parts[7])
        val end_ht = LocalTime.parse(parts[8])
        val start_hq = LocalTime.parse(parts[9])
        val end_hq = LocalTime.parse(parts[10])
        val start_hqi = LocalTime.parse(parts[11])
        val end_hqi = LocalTime.parse(parts[12])
        val start_hse = LocalTime.parse(parts[13])
        val end_hse = LocalTime.parse(parts[14])
        val start_hsa = LocalTime.parse(parts[15])
        val end_hsa = LocalTime.parse(parts[16])

        val dateFormat_hora =
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                LocalTime.now()
            } else {
                TODO("VERSION.SDK_INT < O")
            }

        var hoje = Date()
        var c = Calendar.getInstance()
        c.time = hoje
        var intHoje = c.get(Calendar.DAY_OF_WEEK)

        val semana = when (intHoje) {
            1 -> "DOM"
            2 -> "SEG"
            3 -> "TER"
            4 -> "QUA"
            5 -> "QUI"
            6 -> "SEX"
            7 -> "SAB"
            else -> "Invalido"
        }

        if(semana.equals("seg",true)) {
            if (end_hs > start_hs) {
                if (dateFormat_hora > start_hs && dateFormat_hora < end_hs) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hs
                   fundo_Item.setBackgroundResource(R.color.green)
                } else {
                   horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                   horario_local.text = "    abre as" + start_hs
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_ht == end_ht){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona as Segundas"
                fundo_Item.setBackgroundResource(R.color.red)
            }
            else {
                if (dateFormat_hora > end_hs && dateFormat_hora < start_hs) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hs
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hs
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }
        }else if(semana.equals("ter",true)){
            if (end_ht > start_ht) {
                if (dateFormat_hora > start_ht && dateFormat_hora < end_ht) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_ht
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_ht
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_ht == end_ht){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona as Terças"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_ht && dateFormat_hora < start_ht) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_ht
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_ht
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }else if(semana.equals("qua",true)){
            if (end_hq > start_hq) {
                if (dateFormat_hora > start_hq && dateFormat_hora < end_hq) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hq
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_hq
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_hq == end_hq){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona as Quartas"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_hq && dateFormat_hora < start_hq) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hq
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hq
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }else if(semana.equals("qui",true)){
            if (end_hqi > start_hqi) {
                if (dateFormat_hora > start_hqi && dateFormat_hora < end_hqi) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hqi
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_hqi
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_hqi == end_hqi){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona as Quintas"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_hqi && dateFormat_hora < start_hqi) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hqi
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hqi
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }else if(semana.equals("sex",true)){
            if (end_hse > start_hse) {
                if (dateFormat_hora > start_hse && dateFormat_hora < end_hse) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hse
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_hse
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_hse == end_hse){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona as Sextas"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_hse && dateFormat_hora < start_hse) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hse
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hse
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }else if(semana.equals("sab",true)){
            if (end_hsa > start_hsa) {
                if (dateFormat_hora > start_hsa && dateFormat_hora < end_hsa) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hsa
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_hsa
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_hsa == end_hsa){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona aos Sabados"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_hsa && dateFormat_hora < start_hsa) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hsa
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hsa
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }else if(semana.equals("dom",true)){
            if (end_hd > start_hd) {
                if (dateFormat_hora > start_hd && dateFormat_hora < end_hd) {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hd
                    fundo_Item.setBackgroundResource(R.color.green)
                } else {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as" + start_hd
                    fundo_Item.setBackgroundResource(R.color.red)
                }
            }else if(start_hd == end_hd){
                horario_local_a.visibility = View.INVISIBLE
                horario_local_f.visibility = View.VISIBLE
                horario_local.text = "    Não funciona aos Domingos"
                fundo_Item.setBackgroundResource(R.color.red)
            }

            else {
                if (dateFormat_hora > end_hd && dateFormat_hora < start_hd) {
                    horario_local_a.visibility = View.INVISIBLE
                    horario_local_f.visibility = View.VISIBLE
                    horario_local.text = "    abre as: " + start_hd
                    fundo_Item.setBackgroundResource(R.color.red)

                } else {
                    horario_local_f.visibility = View.INVISIBLE
                    horario_local_a.visibility = View.VISIBLE
                    horario_local.text = " fecha as: " + end_hd
                    fundo_Item.setBackgroundResource(R.color.green)
                }
            }

        }



        tvNome.text = marker.title
        tvDistancia.text = "Distancia: "+parts[0]+" Km"
        tvEntrada.text = parts[1]
        if(parts[1] ==""){
            tvEntrada.text = "Sem entrada"
        }else
        {
            tvEntrada.text = "Entrada/Couvert: "+parts[1]
        }

        var link = parts[2]

        if(link !=""){
            Picasso.get()
                .load(link)
                .into(imgLogo)
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun getInfoWindow(p0: Marker?): View {
        rendowWindowText(p0!! ,mWindow)
        return mWindow
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getInfoContents(p0: Marker?): View {
        rendowWindowText(p0!! ,mWindow)
        return mWindow
    }


}