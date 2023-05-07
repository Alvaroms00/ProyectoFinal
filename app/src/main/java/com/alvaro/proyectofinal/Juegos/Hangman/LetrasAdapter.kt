package com.alvaro.proyectofinal.Juegos.Hangman

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.alvaro.proyectofinal.R

class LetrasAdapter(context: Context?) : BaseAdapter() {
    private val letras: Array<String?> = arrayOfNulls(26)
    private val letrasLayout: LayoutInflater

    init {
        for (i in letras.indices) {
            letras[i] = "" + (i + 'A'.code).toChar()
        }
        letrasLayout = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(i: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(i: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getView(i: Int, view: View, viewGroup: ViewGroup): View {
        val btnLetra: Button = view as Button
        btnLetra.text = letras[i]
        return btnLetra
    }
}