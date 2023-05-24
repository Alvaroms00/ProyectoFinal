package com.alvaro.proyectofinal.Juegos.Hangman

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alvaro.proyectofinal.R
import com.alvaro.proyectofinal.databinding.ActivityHangmanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Transaction
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class HangmanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHangmanBinding
    private lateinit var database: DatabaseReference

    private lateinit var palabraSecreta: String
    private lateinit var palabrasArray: Array<String>

    private var letrasAdivinadas = ArrayList<Char>()
    private var intentosRestantes = 0
    private val letrasPresionadas = mutableListOf<String>()


    private lateinit var txtPalabra: TextView
    private lateinit var imgAhorcado: ImageView
    private lateinit var editTextRespuesta: EditText
    private lateinit var botonComprobar: Button
    private lateinit var gridLetras: GridView
    private lateinit var txtLetrasPresionadas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHangmanBinding.inflate(layoutInflater)
        setContentView(binding.root)



        txtPalabra = binding.txtAdivinar
        imgAhorcado = binding.imgJuegoAhorcado
        editTextRespuesta = binding.editTextRespuesta
        botonComprobar = binding.botonComprobar
        gridLetras = binding.gridLetras
        txtLetrasPresionadas = binding.letrasPresionadas

        botonComprobar.setOnClickListener(this)

        palabrasArray = resources.getStringArray(R.array.palabras)
        palabraSecreta = palabrasArray.random()

        val letrasAdapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, obtenerLetrasAbecedario())
        binding.gridLetras.adapter = letrasAdapter
        binding.gridLetras.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val letra = letrasAdapter.getItem(position)
                letra?.let { comprobarLetra(it) }
            }

        actualizarPalabra()
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.botonComprobar) {
            val respuesta = binding.editTextRespuesta.text.toString().uppercase()
            comprobarRespuesta(respuesta)
        }
    }

    private fun obtenerLetrasAbecedario(): List<String> {
        return listOf(
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "Ñ", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        )
    }

    private fun comprobarLetra(letra: String) {
        val letraChar = letra.uppercase()[0]
        if (!letrasAdivinadas.contains(letraChar)) {
            letrasAdivinadas.add(letraChar)
            if (!palabraSecreta.contains(letraChar)) {
                intentosRestantes++
                letrasPresionadas.add(letra)
                actualizarLetrasPresionadas()
                actualizarImagenAhorcado()
            }
            actualizarPalabra()
            if (intentosRestantes == 6) {
                desactivarBotones()
                dialogoDerrota()
            } else if (palabraAdivinada()) {
                desactivarBotones()
                dialogoVictoria()
            }
        }
    }

    private fun comprobarRespuesta(respuesta: String) {
        if (respuesta == palabraSecreta) {
            letrasAdivinadas.clear()
            letrasAdivinadas.addAll(palabraSecreta.toCharArray().toList())
            actualizarPalabra()

            desactivarBotones()
            dialogoVictoria()
        } else {
            dialogoDerrota()
            desactivarBotones()
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
        val imagenId =
            resources.getIdentifier("ahorcado_$intentosRestantes", "drawable", packageName)
        binding.imgJuegoAhorcado.setImageResource(imagenId)
    }

    private fun desactivarBotones() {
        binding.gridLetras.isEnabled = false
        binding.botonComprobar.isEnabled = false
    }

    private fun obtenerPalabraAleatoria(): String {
        return palabrasArray.random()
    }

    private fun reiniciarJuego() {
        letrasAdivinadas.clear()
        intentosRestantes = 0
        palabraSecreta = obtenerPalabraAleatoria()
        txtLetrasPresionadas.text = "${getString(R.string.txtLetraFallada)} "
        letrasPresionadas.clear()
        binding.editTextRespuesta.setText("")

        actualizarPalabra()
        actualizarImagenAhorcado()

        binding.gridLetras.isEnabled = true
        binding.botonComprobar.isEnabled = true
    }

    private fun actualizarLetrasPresionadas() {
        val letrasPresionadasText = letrasPresionadas.joinToString(", ")
        txtLetrasPresionadas.text = "${getString(R.string.txtLetraFallada)}$letrasPresionadasText"
    }

    private fun actualizarPuntuacion() {
        database =
            FirebaseDatabase.getInstance("https://proyectofinal-fdf7f-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("Usuarios")
        val preferencias = getSharedPreferences("usuario", Context.MODE_PRIVATE)
        val usuario = preferencias.getString("usuario", "")

        if (usuario != null) {
            val referenciaUsuario = database.child(usuario)

            referenciaUsuario.runTransaction(object : Transaction.Handler {
                override fun doTransaction(currentData: MutableData): Transaction.Result {
                    val puntuacionAhorcado = currentData.child("puntuacionAhorcado").getValue(Long::class.java)
                    val puntajeActual = puntuacionAhorcado?.toInt() ?: 0
                    val puntuacion = 100
                    val nuevaPuntuacion = puntajeActual + puntuacion

                    currentData.child("puntuacionAhorcado").value = nuevaPuntuacion

                    return Transaction.success(currentData)
                }

                override fun onComplete(
                    error: DatabaseError?,
                    committed: Boolean,
                    currentData: DataSnapshot?
                ) {
                    if (error != null) {
                        Log.w("Error", "error")
                    } else {
                        Log.i("Correcto", "Correcto")
                    }
                }
            })
        }
    }

    private fun dialogoVictoria() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.victoria)
        builder.setTitle(getString(R.string.victoria))
        builder.setMessage("¡Felicidades!\nHas adivinado la palabra.\n¿Deseas jugar de nuevo?")
        builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
            reiniciarJuego()
            //val puntuacion = obtenerPuntuacion()
            actualizarPuntuacion()
        }
        builder.setNegativeButton(getString(R.string.salir)) { _, _ ->
            finish()
            //val puntuacion = obtenerPuntuacion()
            actualizarPuntuacion()
        }
        builder.show()
    }

    private fun dialogoDerrota() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.derrota)
        builder.setTitle(getString(R.string.derrota))
        builder.setMessage("Has perdido. La palabra era: $palabraSecreta.\n¿Deseas jugar de nuevo?")
        builder.setPositiveButton(getString(R.string.txtJugarDeNuevo)) { _, _ ->
            reiniciarJuego()
        }
        builder.setNegativeButton(R.string.salir) { _, _ ->
            finish()
        }
        builder.show()
    }
}