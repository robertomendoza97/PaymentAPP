package com.example.paymentapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class adaptadorTipoTarjeta(val ctx: Context,
                           val contenido: List<Map<String,String>>
                           ): BaseAdapter() {
    override fun getCount(): Int {
        return contenido.count()
    }

    override fun getItem(position: Int): Any {
        return contenido[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val layout = LayoutInflater.from(ctx)
        val miView = layout.inflate(R.layout.item_spinner,p2, false)
        val img = miView.findViewById<ImageView>(R.id.itemImage)
        val cont = miView.findViewById<TextView>(R.id.nombreItem)
        Glide.with(ctx).load(contenido[position]["imagen"]).placeholder(R.drawable.melilogo).into(img)
        cont.text = contenido[position]["tipo"]

        return miView
    }
}

class adaptadorCuotas(val ctx: Context,
                      val contenido: List<Map<String, String>>): BaseAdapter() {
    override fun getCount(): Int {
        return contenido.count()
    }

    override fun getItem(position: Int): Any {
        return contenido[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, p2: ViewGroup?): View {
        val layout = LayoutInflater.from(ctx)
        val miView = layout.inflate(R.layout.item_spinner2,p2, false)
        val cont = miView.findViewById<TextView>(R.id.itemCuota)
        cont.text = contenido[position]["msj"]

        return miView
    }


}