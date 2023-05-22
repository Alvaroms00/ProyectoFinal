package com.alvaro.proyectofinal.Juegos.Hangman

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityHangmanBinding

class HangmanActivity: AppCompatActivity(), View.OnClickListener  {
    private lateinit var binding: ActivityHangmanBinding


    private val palabraSecreta = "programacion"
    private var letrasAdivinadas = ArrayList<Char>()
    private var intentosRestantes = 6

    private lateinit var txtPalabra: TextView
    private lateinit var imgAhorcado: ImageView
    private lateinit var editTextRespuesta: EditText
    private lateinit var botonComprobar: Button
    private lateinit var gridLetras: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHangmanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        txtPalabra = findViewById(R.id.txtAdivinar)
        imgAhorcado = findViewById(R.id.imgJuegoAhorcado)
        editTextRespuesta = findViewById(R.id.editTextRespuesta)
        botonComprobar = findViewById(R.id.botonComprobar)
        gridLetras = findViewById(R.id.gridLetras)

        botonComprobar.setOnClickListener(this)

        val letrasAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, obtenerLetrasAbecedario())
        gridLetras.adapter = letrasAdapter
        gridLetras.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val letra = letrasAdapter.getItem(position)
                letra?.let { comprobarLetra(it) }
            }

        actualizarPalabra()

    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.botonComprobar) {
            val respuesta = editTextRespuesta.text.toString().uppercase()
            comprobarRespuesta(respuesta)
        }
    }

    private fun obtenerLetrasAbecedario(): List<String> {
        return listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
    }

    private fun comprobarLetra(letra: String) {
        val letraChar = letra.uppercase()[0]
        if (!letrasAdivinadas.contains(letraChar)) {
            letrasAdivinadas.add(letraChar)
            if (!palabraSecreta.contains(letraChar)) {
                intentosRestantes--
                actualizarImagenAhorcado()
            }
            actualizarPalabra()
            if (intentosRestantes == 0 || palabraAdivinada()) {
                desactivarBotones()
            }
        }
    }

    private fun comprobarRespuesta(respuesta: String) {
        if (respuesta == palabraSecreta) {
            letrasAdivinadas.clear()
            letrasAdivinadas.addAll(palabraSecreta.toCharArray().toList())
            actualizarPalabra()

        } else {

        }
    }

    private fun palabraAdivinada(): Boolean {
        for (letra in palabraSecreta) {
            if (!letrasAdivinadas.contains(letra)) {
                return false
            }
        }
        return true
    }

    private fun actualizarPalabra() {
        val palabraMostrada = StringBuilder()
        for (letra in palabraSecreta) {
            if (letrasAdivinadas.contains(letra)) {
                palabraMostrada.append(letra)
            } else {
                palabraMostrada.append("_")
            }
            palabraMostrada.append(" ")
        }
        txtPalabra.text = palabraMostrada.toString()
    }

    private fun actualizarImagenAhorcado() {
        val imagenId = resources.getIdentifier("ahorcado_$intentosRestantes", "drawable", packageName)
        imgAhorcado.setImageResource(imagenId)
    }

    private fun desactivarBotones() {
        gridLetras.isEnabled = false
        botonComprobar.isEnabled = false
    }
}