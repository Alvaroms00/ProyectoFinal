package com.alvaro.proyectofinal.Juegos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.alvaro.proyectofinal.R

class LetrasAdapter(context: Context) : BaseAdapter() {

    private var letras: Array<String> = Array(26) { i -> "${(i + 'A'.code).toChar()}" }
    var letrasLayout: LayoutInflater

    init {
        letrasLayout = LayoutInflater.from(context)
    }
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val btnLetra: Button = if(convertView == null){
            letrasLayout.inflate(R.layout.letras, parent,false) as Button
        } else{
            convertView as Button
        }
        btnLetra.text = letras[position]
        return btnLetra
    }
}